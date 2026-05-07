package com.agent1k.config;

import com.agent1k.EryShulker;
import com.agent1k.utils.HexUtils;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.configuration.file.FileConfiguration;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.stream.Collectors;

public class Configs {

    private final EryShulker plugin;
    private FileConfiguration config;

    public Configs(@NotNull EryShulker plugin) {
        this.plugin = plugin;
        load();
    }

    private void load() {
        plugin.saveDefaultConfig();
        this.config = plugin.getConfig();
    }

    public void reload() {
        plugin.reloadConfig();
        this.config = plugin.getConfig();
    }

    @NotNull
    public String getInventoryName() {
        return HexUtils.colorize(config.getString("settings.shulker.inventory-name"));
    }

    public int getInventorySize() {
        return config.getInt("settings.shulker.size");
    }

    public boolean isLeftClickEnabled() {
        return config.getBoolean("settings.shulker.LEFT");
    }

    public boolean isRightClickEnabled() {
        return config.getBoolean("settings.shulker.RIGHT");
    }

    public boolean isShiftRightEnabled() {
        return config.getBoolean("settings.shulker.shift_right");
    }

    @NotNull
    public Material getOpenShulkerMaterial() {
        String materialName = config.getString("settings.open-shulker.material");
        return Material.valueOf(materialName);
    }

    @NotNull
    public String getOpenShulkerName() {
        return HexUtils.colorize(config.getString("settings.open-shulker.name"));
    }

    public boolean isAutoAllItems() {
        return config.getBoolean("settings.shulker-auto-all-item");
    }

    @NotNull
    public List<Material> getAutoLootMaterials() {
        return config.getStringList("settings.shulker-auto")
                .stream()
                .map(Material::valueOf)
                .collect(Collectors.toList());
    }

    public int getShulkerLimit() {
        return config.getInt("settings.shulker-limit");
    }

    public boolean isAddItemSoundEnabled() {
        return config.getBoolean("sound.addItemShulker.enabled");
    }

    @NotNull
    public Sound getAddItemSound() {
        String soundName = config.getString("sound.addItemShulker.sound");
        try {
            return Sound.valueOf(soundName);
        } catch (IllegalArgumentException e) {
            plugin.getLogger().warning("Звук '" + soundName + "' не найден, используется ENTITY_ITEM_PICKUP");
            return Sound.ENTITY_ITEM_PICKUP;
        }
    }

    public float getAddItemSoundPitch() {
        return (float) config.getDouble("sound.addItemShulker.pitch");
    }

    public float getAddItemSoundVolume() {
        return (float) config.getDouble("sound.addItemShulker.volume");
    }
}