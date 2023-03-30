package dev.lightdream.inventory_manager;

import org.bukkit.entity.Player;

public class InventoryInstance {

    private final Inventory template;
    private final Player player;

    public InventoryInstance(Inventory template, Player player) {
        this.template = template;
        this.player = player;
    }

    private void open() {
        player.openInventory(template.generateBukkitInventory());
    }

}
