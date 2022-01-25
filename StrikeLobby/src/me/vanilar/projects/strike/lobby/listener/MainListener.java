package me.vanilar.projects.strike.lobby.listener;

import me.vanilar.projects.strike.lobby.StrikeLobby;
import me.vanilar.projects.strike.lobby.teams.TeamSelectGui;
import me.vanilar.projects.strike.lobby.utils.Message;
import me.vanilar.projects.strike.lobby.utils.Messager;
import org.bukkit.Material;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;

public class MainListener implements Listener {

    private Messager messager = StrikeLobby.getInstance().getMessager();
    private final String PREFIX = messager.getMessage(Message.PLUGIN_PREFIX);
    private HashMap<String, TeamSelectGui> guis = new HashMap<>();


    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        player.sendMessage("§fПриветствуем на мини-игре §aStrike§f!");
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
