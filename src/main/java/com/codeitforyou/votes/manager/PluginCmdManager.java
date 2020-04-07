package com.codeitforyou.votes.manager;

import com.codeitforyou.lib.api.command.CommandManager;
import com.codeitforyou.votes.Votes;
import com.codeitforyou.votes.command.MainCommand;

import java.util.Arrays;

public class PluginCmdManager {

    private CommandManager commandManager;
    public PluginCmdManager(Votes plugin) {
        commandManager = new CommandManager(Arrays.asList(), plugin.getDescription().getName().toLowerCase(), plugin);
        commandManager.setMainCommand(MainCommand.class);
        commandManager.addAlias("vote");
        commandManager.register();

    }
}
