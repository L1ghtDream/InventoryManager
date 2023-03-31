package dev.lightdream.inventory_manager;

import dev.lightdream.logger.Logger;
import org.bukkit.entity.Player;

import java.util.HashMap;

public class InventoryManager {

    private final InventoryManagerMain main;
    private final HashMap<String, IInventory> inventories = new HashMap<>();

    public InventoryManager(InventoryManagerMain main) {
        this.main = main;
    }

    public void registerInventory(IInventory inventory) {
        if (inventories.containsKey(inventory.getTitle())) {
            throw new RuntimeException("Inventory already registered");
        }
        inventories.put(inventory.getID(), inventory);
    }

    public void registerInventory(IInventory inventory, boolean overrideExisting) {
        if(!overrideExisting){
            registerInventory(inventory);
            return;
        }
        if (inventories.containsKey(inventory.getTitle())) {
            Logger.warn("Overriding existing inventory. This may cause unexpected" +
                    " behavior. If you see this message after a config reload it may be intended" +
                    " as to re-register the inventory with the new config values.");
        }
        inventories.put(inventory.getID(), inventory);
    }

    public void open(String id, Player player) {
        IInventory inventory = inventories.getOrDefault(id, null);

        if (inventory == null) {
            throw new RuntimeException("Inventory not found");
        }

        new InventoryInstance(main, inventory, player);
    }

}
