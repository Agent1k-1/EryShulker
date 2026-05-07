package com.agent1k.config;

import com.agent1k.EryShulker;
import com.agent1k.utils.HexUtils;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.jetbrains.annotations.NotNull;

import java.io.File;

public class MessageConfig {

    private final EryShulker plugin;
    private File file;
    private FileConfiguration config;

    public MessageConfig(@NotNull EryShulker plugin) {
        this.plugin = plugin;
        load();
    }

    private void load() {
        file = new File(plugin.getDataFolder(), "message.yml");

        if (!file.exists()) {
            plugin.saveResource("message.yml", false);
        }

        config = YamlConfiguration.loadConfiguration(file);
    }

    public void reload() {
        config = YamlConfiguration.loadConfiguration(file);
    }

    @NotNull
    public String getReloadMessage() {
        return HexUtils.colorize(config.getString("messages.reload-config"));
    }

    @NotNull
    public String getErrorPermission() {
        return HexUtils.colorize(config.getString("messages.error-permission"));
    }

    @NotNull
    public String getShulkerLimitMessage() {
        return HexUtils.colorize(config.getString("messages.shulker-limit"));
    }
}