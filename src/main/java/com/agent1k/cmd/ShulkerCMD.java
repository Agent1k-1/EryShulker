package com.agent1k.cmd;

import com.agent1k.EryShulker;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class ShulkerCMD implements TabExecutor {

    private final EryShulker plugin;

    public ShulkerCMD(@NotNull EryShulker plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (args.length == 0) {
            return false;
        }

        if (!args[0].equalsIgnoreCase("reload")) {
            return false;
        }

        if (!sender.hasPermission("shulker.reload")) {
            sender.sendMessage(plugin.getMessageConfig().getErrorPermission());
            return true;
        }

        plugin.reloadConfigs();
        sender.sendMessage(plugin.getMessageConfig().getReloadMessage());
        return true;
    }

    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        List<String> completions = new ArrayList<>();

        if (args.length == 1) {
            if (sender.hasPermission("shulker.reload")) {
                completions.add("reload");
            }
        }

        return completions;
    }
}