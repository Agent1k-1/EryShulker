package com.agent1k;

import com.agent1k.cmd.ShulkerCMD;
import com.agent1k.config.Configs;
import com.agent1k.config.MessageConfig;
import com.agent1k.listener.LimitListener;
import com.agent1k.limit.ShulkerLimitManager;
import com.agent1k.listener.InventoryListener;
import com.agent1k.listener.ShulkerProtectionListener;
import com.agent1k.manager.ShulkerManager;
import com.agent1k.open.LeftOpen;
import com.agent1k.open.RightOpen;
import com.agent1k.open.ShiftRight;
import com.agent1k.shulker.AutoLoot;
import org.bukkit.plugin.java.JavaPlugin;

public final class EryShulker extends JavaPlugin {

    private Configs configs;
    private MessageConfig messageConfig;
    private ShulkerManager shulkerManager;
    private ShulkerLimitManager shulkerLimitManager;

    @Override
    public void onEnable() {
        this.configs = new Configs(this);
        this.messageConfig = new MessageConfig(this);
        this.shulkerManager = new ShulkerManager(configs);
        this.shulkerLimitManager = new ShulkerLimitManager(configs, messageConfig);
        registerListeners();
        registerCommands();
    }

    private void registerListeners() {
        getServer().getPluginManager().registerEvents(new InventoryListener(shulkerManager), this);
        getServer().getPluginManager().registerEvents(new LeftOpen(configs, shulkerManager), this);
        getServer().getPluginManager().registerEvents(new RightOpen(configs, shulkerManager), this);
        getServer().getPluginManager().registerEvents(new ShiftRight(configs, shulkerManager), this);
        getServer().getPluginManager().registerEvents(new ShulkerProtectionListener(configs, shulkerManager), this);
        getServer().getPluginManager().registerEvents(new AutoLoot(configs), this);
        getServer().getPluginManager().registerEvents(new LimitListener(shulkerLimitManager), this);
    }

    private void registerCommands() {
        getCommand("eryshulker").setExecutor(new ShulkerCMD(this));
    }

    public void reloadConfigs() {
        this.configs.reload();
        this.messageConfig.reload();
    }

    public Configs getConfigs() {
        return configs;
    }

    public MessageConfig getMessageConfig() {
        return messageConfig;
    }

    public ShulkerManager getShulkerManager() {
        return shulkerManager;
    }

    public ShulkerLimitManager getShulkerLimitManager() {
        return shulkerLimitManager;
    }
}