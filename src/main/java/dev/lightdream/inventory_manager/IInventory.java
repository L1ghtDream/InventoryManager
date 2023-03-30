package dev.lightdream.inventory_manager;

import org.bukkit.Bukkit;

public interface IInventory {

    String getID();

    int getSize();

    String getTitle();

    default org.bukkit.inventory.Inventory generateBukkitInventory() {
        return Bukkit.createInventory(null, getSize(), getTitle());
    }

}
