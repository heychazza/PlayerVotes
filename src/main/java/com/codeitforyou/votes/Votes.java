package com.codeitforyou.votes;

import com.codeitforyou.lib.api.actions.ActionManager;
import com.codeitforyou.lib.api.command.CommandManager;
import com.codeitforyou.votes.manager.PluginCmdManager;
import com.codeitforyou.votes.manager.RewardManager;
import com.codeitforyou.votes.registerable.EventRegisterable;
import com.codeitforyou.votes.util.Lang;
import org.bukkit.plugin.java.JavaPlugin;

public class Votes extends JavaPlugin {
    private final ActionManager ACTION_MANAGER = new ActionManager(this);
    private final PluginCmdManager COMMAND_MANAGER = new PluginCmdManager(this);
    private final RewardManager REWARD_MANAGER = new RewardManager(this);
    private static Votes plugin;

    public ActionManager getActionManager() {
        return ACTION_MANAGER;
    }

    public RewardManager getRewardManager() {
        return REWARD_MANAGER;
    }

    public PluginCmdManager getCommandManager() {
        return COMMAND_MANAGER;
    }

    public static Votes getPlugin() {
        return plugin;
    }

    @Override
    public void onEnable() {
        plugin = this;

        // Config
        saveDefaultConfig();

        // Register Core Components
        register();

        // Load Registerables
        EventRegisterable.register();
    }

    public void register() {
        Lang.init(this);
        reloadConfig();

        // Load Default Actions
        ACTION_MANAGER.addDefaults();

        // Load Rewards
        REWARD_MANAGER.loadRewards();

//        COMMAND_MANAGER

//        loadHooks();
    }

    @Override
    public void onDisable() {

    }
}
