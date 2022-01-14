package me.vanilar.projects.strike.listener;

import me.vanilar.projects.strike.StrikeGame;
import me.vanilar.projects.strike.teams.TeamSelectGui;
import me.vanilar.projects.strike.utils.Message;
import me.vanilar.projects.strike.utils.Messager;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;

public class MainListener implements Listener {
    private HashMap<String, TeamSelectGui> guis = new HashMap<>();
    private Messager messager = StrikeGame.getInstance().getMessager();
    private final String PREFIX = messager.getMessage(Message.PLUGIN_PREFIX);

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        event.setJoinMessage(null);
        if(Bukkit.getServer().getOnlinePlayers().size() > (Bukkit.getMaxPlayers() - 1)) {
            if(!player.hasPermission("strike.entry.bypass")) {
                player.kickPlayer(messager.getMessage(Message.SERVER_IS_FULL));
                return;
            }
        }
        player.setGameMode(GameMode.SPECTATOR);
        player.setMetadata("freeze", new FixedMetadataValue(StrikeGame.getInstance(), 1));
        new BukkitRunnable() {
            @Override
            public void run() {
                guis.put(player.getName(), new TeamSelectGui(player));
            }
        }.runTaskLater(StrikeGame.getInstance(), 20L);
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        event.setQuitMessage(null);
        if(guis.containsKey(event.getPlayer().getName())) {
            guis.get(event.getPlayer().getName()).stopUpdatingGui();
        }
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        double fX = event.getFrom().getX();
        double fY = event.getFrom().getY();
        double fZ = event.getFrom().getZ();

        double tX = event.getTo().getX();
        double tY = event.getTo().getY();
        double tZ = event.getTo().getZ();

        if((fX != tX) || (fY != tY) || (fZ != tZ) && event.getPlayer().hasMetadata("freeze")) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onInventoryClose(InventoryCloseEvent event) {
        HumanEntity player = event.getPlayer();
        if(event.getView().getTitle().equals(messager.getMessage(Message.CHOOSE_COMMAND))) {
            int seconds = 5;
            player.sendMessage(PREFIX+" "+String.format(messager.getMessage(Message.REOPEN_CHOOSE_COMMAND_MENU), seconds));
            new BukkitRunnable(){
                @Override
                public void run() {
                    player.openInventory(guis.get(player.getName()).getGui());
                }
            }.runTaskLater(StrikeGame.getInstance(), (20L*seconds));
        }
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        Inventory gui = event.getView().getTopInventory();
        HumanEntity player = event.getWhoClicked();
        if(event.getView().getTitle().equals(messager.getMessage(Message.CHOOSE_COMMAND))) {
            int clickedSlot = event.getRawSlot();
            if((clickedSlot > gui.getSize()-1) || clickedSlot < 0) {
                event.setCancelled(true);
                return;
            }
            ItemStack button = gui.getItem(clickedSlot);
            if(button != null) {

                if(button.getType() == Material.IRON_HELMET) {
                    player.sendMessage(PREFIX+" "+messager.getMessage(Message.PLAYER_CHOOSE_COUNTER_TERRORISTS_COMMAND));
                }
                else if(button.getType() == Material.LEATHER_HELMET) {
                    player.sendMessage(PREFIX+" "+messager.getMessage(Message.PLAYER_CHOOSE_TERRORISTS_COMMAND));
                }
                else if(button.getType() == Material.ENDER_EYE) {
                    player.sendMessage(PREFIX+" "+messager.getMessage(Message.PLAYER_CHOOSE_VIEWERS_COMMAND));
                }
                guis.get(player.getName()).stopUpdatingGui();
            }
            event.setCancelled(true);
        }
    }
}
