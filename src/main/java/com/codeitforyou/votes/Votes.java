package com.codeitforyou.votes;

import com.codeitforyou.lib.api.actions.ActionManager;
import com.codeitforyou.votes.registerable.EventRegisterable;
import com.codeitforyou.votes.util.Lang;
import org.bukkit.plugin.java.JavaPlugin;
import pw.valaria.requirementsprocessor.RequirementsUtil;

public class Votes extends JavaPlugin {
    private final ActionManager ACTION_MANAGER = new ActionManager(this);
    private static Votes plugin;

    public ActionManager getActionManager() {
        return ACTION_MANAGER;
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

//        loadHooks();
    }

    @Override
    public void onDisable() {

    }
}
