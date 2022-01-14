package me.vanilar.projects.strike.teams;

import me.vanilar.projects.strike.StrikeModule;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.Arrays;
import java.util.Date;

public class TeamSelectGui {

    private Inventory gui;
    private ItemStack cTButton;
    private ItemStack tButton;
    private ItemStack vButton;
    private TeamManager teamManager;
    private Player player;
    private BukkitTask task;

    public TeamSelectGui(Player player) {
        this.player = player;
        teamManager = StrikeModule.getInstance().getTeamManager();
        this.gui = Bukkit.createInventory(null, 9*1, "Выберите команду");
        this.startUpdate();
        this.player.openInventory(this.gui);
    }

    public void stopUpdatingGui() {
        this.task.cancel();
    }

    private void startUpdate() {
        task = new BukkitRunnable() {
            @Override
            public void run() {
                cTButton = new ItemStack(Material.IRON_HELMET);
                cTButton.setItemMeta(editToButtonMeta(cTButton.getItemMeta(), "§aКонтртеррористы", TeamType.COUNTER_TERRORISTS, true));

                tButton = new ItemStack(Material.LEATHER_HELMET);
                tButton.setItemMeta(editToButtonMeta(tButton.getItemMeta(), "§aТеррористы", TeamType.TERRORISTS, true));

                vButton = new ItemStack(Material.EYE_OF_ENDER);
                vButton.setItemMeta(editToButtonMeta(vButton.getItemMeta(), "§aЗрители", TeamType.VIEWERS, false));

                gui.setItem(2, cTButton);
                gui.setItem(4, vButton);
                gui.setItem(6, tButton);
            }
        }.runTaskTimer(StrikeModule.getInstance(), 20L, 20L);
    }

    private ItemMeta editToButtonMeta(ItemMeta meta, String title, TeamType type, boolean isRestrictedSize) {
        ItemMeta result = meta;
        int count = 0;
        int maxPlayers = teamManager.getMaxTeamSize();
        switch(type) {
            case COUNTER_TERRORISTS:
                count = teamManager.getCTTeamPlayersCount();
                break;
            case TERRORISTS:
                count = teamManager.getTTeamPlayersCount();
                break;
            case VIEWERS:
                count = teamManager.getVTeamPlayersCount();
                break;
             default:
                 break;
        }
        result.setDisplayName(title);
        Date date = new Date();
        String time = date.getHours() + "§f:§a" + date.getMinutes() + "§f:§a" + date.getSeconds();
        result.setLore(Arrays.asList("", "§fУчастников: §a" + (isRestrictedSize ? (count + "§f/§a" + maxPlayers):(count)), "§fВремя: §a" + time));
        for(ItemFlag flag : ItemFlag.values()) {
            if(flag == null) continue;
            if(result.hasItemFlag(flag)) continue;
            result.addItemFlags(flag);
        }
        return result;
    }

    public Inventory getGui() {
        return this.gui;
    }
}
