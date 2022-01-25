package me.vanilar.projects.strike;

import me.vanilar.projects.strike.commands.LobbyCommand;
import me.vanilar.projects.strike.listener.MainListener;
import me.vanilar.projects.strike.room.maps.MapsManager;
import me.vanilar.projects.strike.teams.TeamManager;
import me.vanilar.projects.strike.utils.Messager;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;

public class StrikeGame extends JavaPlugin {

    private static StrikeGame instance;
    private TeamManager teamManager;
    private MapsManager mapsManager;
    private Messager messager;

    @Override
    public void onEnable() {
        instance = this;
        Bukkit.getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");
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
        teamManager = new TeamManager(this.getConfig().getConfigurationSection("settings.teams"));
        mapsManager = new MapsManager();
        messager = new Messager(this.getConfig());
        Bukkit.getPluginManager().registerEvents(new MainListener(), this);
        this.getCommand("lobby").setExecutor(new LobbyCommand());
        this.getLogger().info("§aPlugin is enabled");
    }
    @Override
    public void onDisable() {
        this.getLogger().info("§6Module is disabled");
    }

    public static StrikeGame getInstance() {
        return instance;
    }

    public TeamManager getTeamManager() {
        return this.teamManager;
    }

    public MapsManager getMapsManager() {
        return this.mapsManager;
    }

    public Messager getMessager() {
        return this.messager;
    }
}
