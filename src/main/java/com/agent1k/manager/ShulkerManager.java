package com.agent1k.manager;

import com.agent1k.config.Configs;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class ShulkerManager {

    private final Configs configs;
    private final Map<UUID, ItemStack> originalShulkers;

    public ShulkerManager(@NotNull Configs configs) {
        this.configs = configs;
        this.originalShulkers = new HashMap<>();
    }

    public void replaceShulker(@NotNull Player player) {
        ItemStack originalShulker = player.getInventory().getItemInMainHand();
        originalShulkers.put(player.getUniqueId(), originalShulker.clone());

        ItemStack replacement = createReplacementItem();
        player.getInventory().setItemInMainHand(replacement);
    }

    public void restoreShulker(@NotNull Player player) {
        ItemStack original = originalShulkers.remove(player.getUniqueId());

        if (original == null) {
            return;
        }

        player.getInventory().setItemInMainHand(original);
    }

    public boolean hasOpenShulker(@NotNull Player player) {
        return originalShulkers.containsKey(player.getUniqueId());
    }

    @NotNull
    public ItemStack getOriginalShulker(@NotNull Player player) {
        return originalShulkers.getOrDefault(player.getUniqueId(), new ItemStack(Material.AIR));
    }

    @NotNull
    private ItemStack createReplacementItem() {
        ItemStack item = new ItemStack(configs.getOpenShulkerMaterial());
        ItemMeta meta = item.getItemMeta();

        if (meta != null) {
            meta.setDisplayName(configs.getOpenShulkerName());
            item.setItemMeta(meta);
        }

        return item;
    }
}