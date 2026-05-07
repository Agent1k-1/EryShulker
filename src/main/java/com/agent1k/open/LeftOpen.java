package com.agent1k.open;

import com.agent1k.config.Configs;
import com.agent1k.manager.ShulkerManager;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BlockStateMeta;
import org.jetbrains.annotations.NotNull;

public class LeftOpen implements Listener {

    private final Configs configs;
    private final ShulkerManager shulkerManager;

    public LeftOpen(@NotNull Configs configs, @NotNull ShulkerManager shulkerManager) {
        this.configs = configs;
        this.shulkerManager = shulkerManager;
    }

    @EventHandler
    public void onLeftClick(@NotNull PlayerInteractEvent event) {
        if (!configs.isLeftClickEnabled()) {
            return;
        }

        Player player = event.getPlayer();
        ItemStack item = event.getItem();

        if (!isValidInteraction(event.getAction(), item)) {
            return;
        }

        if (!isShulkerBox(item.getType())) {
            return;
        }

        event.setCancelled(true);
        openShulkerInventory(player, item);
    }

    private boolean isValidInteraction(@NotNull Action action, ItemStack item) {
        return action == Action.LEFT_CLICK_AIR && item != null;
    }

    private boolean isShulkerBox(@NotNull Material material) {
        return material.name().endsWith("SHULKER_BOX");
    }

    private void openShulkerInventory(@NotNull Player player, @NotNull ItemStack shulker) {
        BlockStateMeta meta = (BlockStateMeta) shulker.getItemMeta();

        if (meta == null) {
            return;
        }

        org.bukkit.block.ShulkerBox shulkerBox = (org.bukkit.block.ShulkerBox) meta.getBlockState();
        Inventory inventory = player.getServer().createInventory(player, configs.getInventorySize(), configs.getInventoryName());
        inventory.setContents(shulkerBox.getInventory().getContents());

        shulkerManager.replaceShulker(player);
        player.openInventory(inventory);
    }
}