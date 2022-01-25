package me.vanilar.projects.strike.lobby;

import me.vanilar.projects.strike.lobby.commands.MainCommand;
import me.vanilar.projects.strike.lobby.listener.MainListener;
import me.vanilar.projects.strike.lobby.teams.TeamManager;
import me.vanilar.projects.strike.lobby.utils.Messager;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;

public class StrikeLobby extends JavaPlugin {

    private static StrikeLobby instance;
    private TeamManager teamManager;
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
        messager = new Messager(this.getConfig());

        Bukkit.getPluginManager().registerEvents(new MainListener(), this);
        this.getCommand("strikelobby").setExecutor(new MainCommand());
        this.getLogger().info("§aPlugin is enabled");
    }

    public TeamManager getTeamManager() {
        return this.teamManager;
    }

    public Messager getMessager() {
        return this.messager;
    }

    public static StrikeLobby getInstance() {
        return instance;
    }
}
