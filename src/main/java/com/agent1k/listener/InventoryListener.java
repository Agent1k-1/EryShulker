package com.agent1k.listener;

import com.agent1k.manager.ShulkerManager;
import org.bukkit.Material;
import org.bukkit.block.ShulkerBox;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BlockStateMeta;
import org.jetbrains.annotations.NotNull;

public class InventoryListener implements Listener {

    private final ShulkerManager shulkerManager;

    public InventoryListener(@NotNull ShulkerManager shulkerManager) {
        this.shulkerManager = shulkerManager;
    }

    @EventHandler
    public void onInventoryClose(@NotNull InventoryCloseEvent event) {
        Player player = (Player) event.getPlayer();

        if (!shulkerManager.hasOpenShulker(player)) {
            return;
        }

        Inventory inventory = event.getInventory();
        ItemStack originalShulker = shulkerManager.getOriginalShulker(player);

        saveShulkerContents(originalShulker, inventory);
        shulkerManager.restoreShulker(player);
    }

    private void saveShulkerContents(@NotNull ItemStack shulker, @NotNull Inventory inventory) {
        BlockStateMeta meta = (BlockStateMeta) shulker.getItemMeta();

        if (meta == null) {
            return;
        }

        ShulkerBox shulkerBox = (ShulkerBox) meta.getBlockState();
        shulkerBox.getInventory().setContents(inventory.getContents());
        meta.setBlockState(shulkerBox);
        shulker.setItemMeta(meta);
    }
}