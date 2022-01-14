package me.vanilar.projects.strike;

import me.vanilar.projects.strike.listener.MainListener;
import me.vanilar.projects.strike.room.maps.MapsManager;
import me.vanilar.projects.strike.teams.TeamManager;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;

public class StrikeModule extends JavaPlugin {

    private static StrikeModule instance;
    private TeamManager teamManager;
    private MapsManager mapsManager;

    @Override
    public void onEnable() {
        instance = this;
        File config = new File(this.getDataFolder(), "config.yml");
        if (!this.getDataFolder().exists()) {
            this.getDataFolder().mkdirs();
            this.saveDefaultConfig();
        }
        else {
            if (!config.exists()) {
                try {
                    config.createNewFile();
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        Bukkit.getPluginManager().registerEvents(new MainListener(), this);
        teamManager = new TeamManager(this.getConfig().getConfigurationSection("settings.teams"));
        mapsManager = new MapsManager();
        this.getLogger().info("§aModule is enabled");
    }
    @Override
    public void onDisable() {
        this.getLogger().info("§6Module is disabled");
    }

    public static StrikeModule getInstance() {
        return instance;
    }

    public TeamManager getTeamManager() {
        return this.teamManager;
    }

    public MapsManager getMapsManager() {
        return this.mapsManager;
    }
}
