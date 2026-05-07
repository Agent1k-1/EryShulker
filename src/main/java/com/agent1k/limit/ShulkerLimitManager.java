package com.agent1k.limit;

import com.agent1k.config.Configs;
import com.agent1k.config.MessageConfig;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;

public class ShulkerLimitManager {

    private final Configs configs;
    private final MessageConfig messageConfig;

    public ShulkerLimitManager(@NotNull Configs configs, @NotNull MessageConfig messageConfig) {
        this.configs = configs;
        this.messageConfig = messageConfig;
    }

    public boolean isShulkerBox(@NotNull ItemStack item) {
        return item.getType().name().endsWith("SHULKER_BOX");
    }

    public int countShulkers(@NotNull Player player) {
        return (int) Arrays.stream(player.getInventory().getContents())
                .filter(item -> item != null && isShulkerBox(item))
                .count();
    }

    public boolean isLimitReached(@NotNull Player player) {
        return countShulkers(player) >= configs.getShulkerLimit();
    }

    public String getLimitMessage() {
        return messageConfig.getShulkerLimitMessage();
    }

    public boolean checkAndNotify(@NotNull Player player, @NotNull ItemStack item) {
        if (!isShulkerBox(item)) {
            return false;
        }
        if (isLimitReached(player)) {
            player.sendMessage(messageConfig.getShulkerLimitMessage());
            return true;
        }
        return false;
    }
}