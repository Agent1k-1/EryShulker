package com.agent1k.listener;

import com.agent1k.config.Configs;
import com.agent1k.manager.ShulkerManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.PlayerInventory;
import org.jetbrains.annotations.NotNull;

public class ShulkerProtectionListener implements Listener {

    private final Configs configs;
    private final ShulkerManager shulkerManager;

    public ShulkerProtectionListener(@NotNull Configs configs, @NotNull ShulkerManager shulkerManager) {
        this.configs = configs;
        this.shulkerManager = shulkerManager;
    }

    @EventHandler
    public void onInventoryClick(@NotNull InventoryClickEvent event) {
        if (!(event.getWhoClicked() instanceof Player)) {
            return;
        }

        Player player = (Player) event.getWhoClicked();

        if (!shulkerManager.hasOpenShulker(player)) {
            return;
        }

        Inventory clickedInventory = event.getClickedInventory();

        if (clickedInventory instanceof PlayerInventory) {
            if (isShulkerInventoryOpen(event.getView().getTopInventory())) {
                if (isShulkerSlot(event.getSlot(), player)) {
                    event.setCancelled(true);
                }
            }
        }
    }

    @EventHandler
    public void onInventoryDrag(@NotNull InventoryDragEvent event) {
        if (!(event.getWhoClicked() instanceof Player)) {
            return;
        }

        Player player = (Player) event.getWhoClicked();

        if (!shulkerManager.hasOpenShulker(player)) {
            return;
        }

        if (!isShulkerInventoryOpen(event.getView().getTopInventory())) {
            return;
        }

        for (int slot : event.getRawSlots()) {
            if (slot >= event.getView().getTopInventory().getSize()) {
                int playerSlot = slot - event.getView().getTopInventory().getSize();
                if (isShulkerSlot(playerSlot, player)) {
                    event.setCancelled(true);
                    return;
                }
            }
        }
    }

    private boolean isShulkerInventoryOpen(@NotNull Inventory inventory) {
        return inventory.getSize() == configs.getInventorySize();
    }

    private boolean isShulkerSlot(int slot, @NotNull Player player) {
        return player.getInventory().getHeldItemSlot() == slot;
    }
}