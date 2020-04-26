package com.codeitforyou.votes;

import com.codeitforyou.lib.api.actions.ActionManager;
import com.codeitforyou.votes.command.PluginCommandManager;
import com.codeitforyou.votes.hook.PAPIHook;
import com.codeitforyou.votes.manager.RewardManager;
import com.codeitforyou.votes.manager.UserManager;
import com.codeitforyou.votes.registerable.EventRegisterable;
import com.codeitforyou.votes.registerable.RequirementRegisterable;
import com.codeitforyou.votes.storage.type.MySQLMapper;
import com.codeitforyou.votes.storage.type.SQLiteMapper;
import com.codeitforyou.votes.storage.StorageType;
import com.codeitforyou.votes.util.Lang;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.plugin.java.JavaPlugin;

public class Votes extends JavaPlugin {
    private final ActionManager ACTION_MANAGER = new ActionManager(this);
    private final PluginCommandManager COMMAND_MANAGER = new PluginCommandManager();
    private final RewardManager REWARD_MANAGER = new RewardManager(this);
    private final UserManager USER_MANAGER = new UserManager();
    private static Votes plugin;

    public ActionManager getActionManager() {
        return ACTION_MANAGER;
    }

    public RewardManager getRewardManager() {
        return REWARD_MANAGER;
    }

    public PluginCommandManager getCommandManager() {
        return COMMAND_MANAGER;
    }

    public UserManager getUserManager() {
        return USER_MANAGER;
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
        RequirementRegisterable.register();
    }

    public void register() {
        Lang.init(this);
        reloadConfig();

        // Load Commands
        COMMAND_MANAGER.register();

        // Load Default Actions
        ACTION_MANAGER.addDefaults();

        // Load Rewards
        REWARD_MANAGER.loadRewards();

        loadHooks();
        loadStorage();
    }

    public void loadHooks() {
        new PAPIHook();
    }

    private StorageType storageType;

    public StorageType getStorageType() {
        return storageType;
    }

    public void loadStorage() {
        ConfigurationSection storageSection = getConfig().getConfigurationSection("settings.storage");
        String storageTypeStr = storageSection.getString("type").toUpperCase();

        switch (storageTypeStr) {
            case "MYSQL":
                storageType = new MySQLMapper(storageSection.getString("prefix"),
                        storageSection.getString("host"),
                        storageSection.getInt("port"),
                        storageSection.getString("database"),
                        storageSection.getString("username"),
                        storageSection.getString("password")
                );
                break;
            case "SQLITE":
                storageType = new SQLiteMapper(storageSection.getString("prefix"));
                break;
            default:
                getLogger().warning("The storage type " + storageTypeStr + " is invalid! Disabling..");
                getServer().getPluginManager().disablePlugin(this);
                break;

        }

        getLogger().info("Using " + storageTypeStr.toLowerCase() + " for user storage!");

    }

    @Override
    public void onDisable() {

    }
}
