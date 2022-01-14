package me.vanilar.projects.strike.lobby;

import me.vanilar.projects.strike.lobby.listener.MainListener;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class StrikeLobby extends JavaPlugin {

    @Override
    public void onEnable() {
        Bukkit.getPluginManager().registerEvents(new MainListener(), this);
        this.getLogger().info("§aPlugin is enabled");
    }
}
