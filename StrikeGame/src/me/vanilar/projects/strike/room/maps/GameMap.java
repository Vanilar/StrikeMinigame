package me.vanilar.projects.strike.room.maps;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.ConfigurationSection;

import java.util.ArrayList;

public class GameMap {

    private ArrayList<Location> counterTerroristCommandSpawnPoints = new ArrayList<>();
    private ArrayList<Location> terroristCommandSpawnPoints = new ArrayList<>();
    private ArrayList<Location> viewersCommandSpawnPoints = new ArrayList<>();
    private String name;
    private World world;
    private String id;

    public GameMap(ConfigurationSection section) {
        this.id = section.getName();
        this.name = section.getString("name");
        this.world = Bukkit.getServer().getWorld(section.getString("world"));
        for(String data : section.getStringList("spawnpoints")) {
            this.counterTerroristCommandSpawnPoints.add(this.parseLocation(data));
            this.terroristCommandSpawnPoints.add(this.parseLocation(data));
            this.viewersCommandSpawnPoints.add(this.parseLocation(data));
        }
    }

    private Location parseLocation(String data) {
        String[] axes = data.split("\\.");
        Double x = Double.parseDouble(axes[0]) + 0.5;
        Double y = Double.parseDouble(axes[1]) + 0.5;
        Double z = Double.parseDouble(axes[2]);
        Location location = new Location(this.world, x, y, z);
        return location;
    }

    public String getName() {
        return this.name;
    }

    public String getId() {
        return this.id;
    }

    public World getWorld() {
        return this.world;
    }

    public ArrayList<Location> getConterTerroristCommandSpawnPoints() {
        return this.counterTerroristCommandSpawnPoints;
    }

    public ArrayList<Location> getTerroristCommandSpawnPoints() {
        return this.terroristCommandSpawnPoints;
    }

    public ArrayList<Location> getViewersCommandSpawnPoints() {
        return this.viewersCommandSpawnPoints;
    }
}
