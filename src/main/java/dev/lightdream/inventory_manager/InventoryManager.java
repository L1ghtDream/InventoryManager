package dev.lightdream.inventory_manager;

import org.bukkit.entity.Player;

import java.util.HashMap;

public class InventoryManager {

    private final HashMap<String, Inventory> inventories = new HashMap<>();

    public void registerInventory(Inventory inventory) {
        if(!inventories.containsKey(inventory.getTitle())){
            throw new RuntimeException("Inventory already registered");
        }
        inventories.put(inventory.getTitle(), inventory);
    }

    public void open(String id, Player player){
        Inventory inventory = inventories.get(id);

        if (inventory == null) {
            throw new RuntimeException("Inventory not found");
        }

        new InventoryInstance(inventory, player);
    }

}
