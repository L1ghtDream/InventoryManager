package dev.lightdream.inventory_manager;

import dev.lightdream.lambda.lambda.ArgLambdaExecutor;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 *
 */
public class Inventory {

    private final @Getter int size;
    private final @Getter String title;

    private final List<ArgLambdaExecutor<InventoryClickEvent>> inventoryClickEventList = new ArrayList<>();
    private final List<ArgLambdaExecutor<InventoryCloseEvent>> inventoryCloseEventList = new ArrayList<>();

    public Inventory(int size, String title) {
        this.size = size;
        this.title = title;
    }

    public Inventory(InventorySize size, String title) {
        this.size = size.getSize();
        this.title = title;
    }

    public org.bukkit.inventory.Inventory generateBukkitInventory() {
        return Bukkit.createInventory(null, size, title);
    }

    private void initListeners() {
        for (Method method : getClass().getMethods()) {
            if (method.getParameterCount() != 1 ||
                    method.getName().equals("internalOnClick") ||
                    method.getName().equals("internalOnClose")) {
                continue;
            }
            if (method.getParameterTypes()[0].equals(InventoryClickEvent.class)) {
                inventoryClickEventList.add(event -> {
                    try {
                        method.invoke(this);
                    } catch (IllegalAccessException | InvocationTargetException e) {
                        throw new RuntimeException(e);
                    }
                });
            } else if (method.getParameterTypes()[0].equals(InventoryCloseEvent.class)) {
                inventoryCloseEventList.add(event -> {
                    try {
                        method.invoke(this);
                    } catch (IllegalAccessException | InvocationTargetException e) {
                        throw new RuntimeException(e);
                    }
                });
            }
        }
    }

    private void internalOnClick(InventoryClickEvent event) {
        for (ArgLambdaExecutor<InventoryClickEvent> executor : inventoryClickEventList) {
            executor.execute(event);
        }
    }

    private void internalOnClose(InventoryCloseEvent event) {
        for (ArgLambdaExecutor<InventoryCloseEvent> executor : inventoryCloseEventList) {
            executor.execute(event);
        }
    }

    private enum InventorySize {
        SIZE_1(9),
        SIZE_2(9 * 2),
        SIZE_3(9 * 3),
        SIZE_4(9 * 4),
        SIZE_5(9 * 5),
        SIZE_6(9 * 6);

        private final @Getter int size;

        InventorySize(int size) {
            this.size = size;
        }
    }


}
