package dev.lightdream.inventory_manager;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class InventoryInstance {

    private final InventoryManagerMain main;
    private final IInventory template;
    private final Player player;

    public InventoryInstance(InventoryManagerMain main, IInventory template, Player player) {
        this.main = main;
        this.template = template;
        this.player = player;
        open();
    }

    private void open() {
        Bukkit.getScheduler().runTask(main.getPlugin(), () -> {
            player.openInventory(template.generateBukkitInventory());
        });
    }

}
