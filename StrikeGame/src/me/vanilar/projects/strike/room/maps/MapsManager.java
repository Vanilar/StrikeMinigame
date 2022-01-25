package me.vanilar.projects.strike.room.maps;

import me.vanilar.projects.strike.StrikeGame;
import me.vanilar.projects.strike.room.maps.gui.MapChooseGui;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.Inventory;

import java.io.File;
import java.util.*;

public class MapsManager {

    private ArrayList<GameMap> maps = new ArrayList<>();
    private HashMap<String, MapChooseGui> mapGuis = new HashMap<>();
    private FileConfiguration mapsConfig;
    private File file;
    private final String PREFIX = "[MapsManager] ";

    public MapsManager() {
        this.loadMaps();
    }

    private void loadMaps() {
        this.file = new File(StrikeGame.getInstance().getDataFolder(), "maps.yml");
        if(!file.exists()) {
            StrikeGame.getInstance().saveResource("maps.yml", false);
        }
        this.mapsConfig = YamlConfiguration.loadConfiguration(file);
        if(!this.mapsConfig.isSet("maps.lobby")) {
            StrikeGame.getInstance().getLogger().severe("Lobby map does not exists!");
            StrikeGame.getInstance().getLogger().info("Disabling plugin!");
            Bukkit.getPluginManager().disablePlugin(StrikeGame.getInstance());
        }
        Set<String> mapsIds = mapsConfig.getConfigurationSection("maps").getKeys(false);
        for(String mapId : mapsIds) {
            GameMap gameMap = new GameMap(mapsConfig.getConfigurationSection("maps."+mapId));
            World world = Bukkit.getServer().getWorld(mapsConfig.getString("maps."+ mapId+ ".world"));
            if(world == null) {
                if(Bukkit.getServer().createWorld(new WorldCreator(mapId)) == null) {
                    StrikeGame.getInstance().getLogger().info(PREFIX + "cannot load map '" + gameMap.getId() + "' - world is not exists");
                }
                else {
                    StrikeGame.getInstance().getLogger().info(PREFIX + "successful loaded map '" + gameMap.getId() + "'");
                }
                continue;
            }
            StrikeGame.getInstance().getLogger().info(PREFIX + "successful loaded map '" + gameMap.getId() + "'");
            this.maps.add(gameMap);
        }
    }

    public void addMapGui(String name, MapChooseGui gui) {
        this.mapGuis.put(name, gui);
    }

    public MapChooseGui getMapGui(String name) {
        return this.mapGuis.get(name);
    }

    public boolean isPlayerHasMapGui(String name) {
        if(this.mapGuis.containsKey(name)) return true;
        else return false;
    }

    public ArrayList<GameMap> getMaps() {
        return this.maps;
    }
}
