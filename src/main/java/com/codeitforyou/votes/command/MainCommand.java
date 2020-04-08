package com.codeitforyou.votes.command;

import com.codeitforyou.lib.api.command.Command;
import com.codeitforyou.votes.Votes;
import com.codeitforyou.votes.util.Lang;
import org.bukkit.entity.Player;

public class MainCommand {
    @Command()
    public static void execute(final Player sender, final Votes plugin, final String[] args) {
        Lang.MAIN_COMMAND.send(sender, Lang.PREFIX.asString(), plugin.getUserManager().getUser(sender.getUniqueId()).getVotes());
//        Lang.MAIN_COMMAND.send(sender, Lang.PREFIX.asString(), plugin.getDescription().getName(), plugin.getDescription().getVersion(), plugin.getDescription().getAuthors().get(0));
    }
}
