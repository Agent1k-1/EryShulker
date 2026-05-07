package com.agent1k.listener;

import com.agent1k.limit.ShulkerLimitManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerAttemptPickupItemEvent;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public class LimitListener implements Listener {

    private final ShulkerLimitManager limitManager;

    public LimitListener(@NotNull ShulkerLimitManager limitManager) {
        this.limitManager = limitManager;
    }

    @EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
    public void onPickup(@NotNull PlayerAttemptPickupItemEvent event) {
        Player player = event.getPlayer();
        ItemStack item = event.getItem().getItemStack();

        if (!limitManager.isShulkerBox(item)) {
            return;
        }

        if (limitManager.isLimitReached(player)) {
            event.setCancelled(true);
            player.sendMessage(limitManager.getLimitMessage());
        }
    }
}