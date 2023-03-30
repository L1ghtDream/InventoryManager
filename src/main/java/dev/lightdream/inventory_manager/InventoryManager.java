package dev.lightdream.inventory_manager;

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

    public void open(String id, Player player) {
        IInventory inventory = inventories.getOrDefault(id, null);

        if (inventory == null) {
            throw new RuntimeException("Inventory not found");
        }

        new InventoryInstance(main, inventory, player);
    }

}
