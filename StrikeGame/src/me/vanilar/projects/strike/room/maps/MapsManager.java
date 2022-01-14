package me.vanilar.projects.strike.room.maps;

import com.mysql.fabric.xmlrpc.base.Array;
import me.vanilar.projects.strike.StrikeModule;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.util.*;

public class MapsManager {

    private ArrayList<GameMap> maps = new ArrayList<>();
    private FileConfiguration mapsConfig;
    private File file;
    private final String PREFIX = "[MapsManager] ";

    public MapsManager() {
        this.loadMaps();
    }

    private void loadMaps() {
        this.file = new File(StrikeModule.getInstance().getDataFolder(), "maps.yml");
        if(!file.exists()) {
            StrikeModule.getInstance().saveResource("maps.yml", false);
        }
        this.mapsConfig = YamlConfiguration.loadConfiguration(file);
        if(!this.mapsConfig.isSet("maps.lobby")) {
            StrikeModule.getInstance().getLogger().severe("Lobby map does not exists!");
            StrikeModule.getInstance().getLogger().info("Disabling plugin!");
            Bukkit.getPluginManager().disablePlugin(StrikeModule.getInstance());
        }
        Set<String> mapsIds = mapsConfig.getConfigurationSection("maps").getKeys(false);
        for(String mapId : mapsIds) {
            GameMap gameMap = new GameMap(mapsConfig.getConfigurationSection("maps."+mapId));
            StrikeModule.getInstance().getLogger().info(PREFIX + "successful loaded map '" + gameMap.getId() + "'");
            this.maps.add(gameMap);
        }
    }

    public ArrayList<GameMap> getMaps() {
        return this.maps;
    }
}
