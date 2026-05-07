package com.agent1k.shulker;

import com.agent1k.config.Configs;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.event.player.PlayerAttemptPickupItemEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BlockStateMeta;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;

public class AutoLoot implements Listener {

    private final Configs configs;

    public AutoLoot(@NotNull Configs configs) {
        this.configs = configs;
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onPickupAttempt(@NotNull PlayerAttemptPickupItemEvent event) {
        Player player = event.getPlayer();
        ItemStack item = event.getItem().getItemStack();

        if (!shouldAutoLoot(item)) {
            return;
        }

        if (player.getInventory().firstEmpty() == -1 && tryAddToShulker(player, item)) {
            event.setCancelled(true);
            event.getItem().remove();
            playAddItemSound(player);
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onItemPickup(@NotNull EntityPickupItemEvent event) {
        if (!(event.getEntity() instanceof Player)) {
            return;
        }

        Player player = (Player) event.getEntity();
        ItemStack item = event.getItem().getItemStack();

        if (!shouldAutoLoot(item)) {
            return;
        }

        if (player.getInventory().firstEmpty() == -1 && tryAddToShulker(player, item)) {
            event.setCancelled(true);
            event.getItem().remove();
            playAddItemSound(player);
        }
    }

    private boolean shouldAutoLoot(@NotNull ItemStack item) {
        if (!configs.isAutoAllItems()) {
            return true;
        }
        return configs.getAutoLootMaterials().contains(item.getType());
    }

    private boolean tryAddToShulker(@NotNull Player player, @NotNull ItemStack item) {
        return Arrays.stream(player.getInventory().getContents())
                .filter(inv -> inv != null && inv.getType().name().endsWith("SHULKER_BOX"))
                .anyMatch(shulker -> {
                    BlockStateMeta meta = (BlockStateMeta) shulker.getItemMeta();
                    if (meta == null) return false;
                    org.bukkit.block.ShulkerBox box = (org.bukkit.block.ShulkerBox) meta.getBlockState();
                    if (box.getInventory().addItem(item).isEmpty()) {
                        meta.setBlockState(box);
                        shulker.setItemMeta(meta);
                        player.updateInventory();
                        return true;
                    }
                    return false;
                });
    }

    private void playAddItemSound(@NotNull Player player) {
        if (!configs.isAddItemSoundEnabled()) {
            return;
        }
        player.playSound(
                player.getLocation(),
                configs.getAddItemSound(),
                configs.getAddItemSoundVolume(),
                configs.getAddItemSoundPitch()
        );
    }
}