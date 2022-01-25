package me.vanilar.projects.strike.room.maps.gui;

import me.vanilar.projects.strike.StrikeGame;
import me.vanilar.projects.strike.room.maps.GameMap;
import me.vanilar.projects.strike.room.maps.MapsManager;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class MapChooseGui {

    private Inventory gui;
    private MapsManager mapsManager;

    public MapChooseGui() {
        this.mapsManager = StrikeGame.getInstance().getMapsManager();
        this.gui = Bukkit.createInventory(null, InventoryType.DROPPER, "Выбирете карту");
        ItemStack icon = new ItemStack(Material.MAP);
        ItemMeta meta = icon.getItemMeta();
        int id = 0;
        for(GameMap map : this.mapsManager.getMaps()) {
            String name = map.getName();
            meta.setDisplayName(name);
            icon.setItemMeta(meta);
            this.gui.setItem(id, icon);
            id += 1;
        }
    }

    public Inventory getGui() {
        return this.gui;
    }
}
