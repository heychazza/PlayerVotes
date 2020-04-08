package com.codeitforyou.votes.command;

import com.codeitforyou.lib.api.command.CommandManager;
import com.codeitforyou.votes.Votes;
import com.codeitforyou.votes.command.sub.FakeVoteCommand;
import com.codeitforyou.votes.command.sub.HelpCommand;
import com.codeitforyou.votes.command.sub.ReloadCommand;
import com.codeitforyou.votes.util.Lang;

import java.util.Arrays;

public class PluginCommandManager {
    private static CommandManager commandManager;

    public void register() {
        commandManager = new CommandManager(Arrays.asList(
                HelpCommand.class,
                ReloadCommand.class,
                FakeVoteCommand.class
        ), Votes.getPlugin().getName().toLowerCase(), Votes.getPlugin());
        commandManager.setMainCommand(MainCommand.class);


        for (String alias : Arrays.asList("chat", "chats")) {
            commandManager.addAlias(alias);
        }

        commandManager.register();
        commandManager.getLocale().setNoPermission(Lang.ERROR_NO_PERMISSION_COMMAND.asString(Lang.PREFIX.asString()));
        commandManager.getLocale().setUnknownCommand("&b[&l" + "Votes" + "&b] &7Unknown sub-command, use &b/chat help&7.");
        commandManager.getLocale().setPlayerOnly(Lang.ERROR_PLAYER_ONLY.asString(Lang.PREFIX.asString()));
    }

    public static CommandManager getCommandManager() {
        return commandManager;
    }
}
