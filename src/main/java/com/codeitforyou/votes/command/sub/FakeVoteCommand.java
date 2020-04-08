package com.codeitforyou.votes.command.sub;

import com.codeitforyou.lib.api.command.Command;
import com.codeitforyou.votes.Votes;
import com.vexsoftware.votifier.model.Vote;
import com.vexsoftware.votifier.model.VotifierEvent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.Date;

public class FakeVoteCommand {

    @Command(aliases = {"fakevote"}, about = "Create a fake vote.", permission = "cifyvotes.fakevote", usage = "fakevote", title = "Fake Vote")
    public static void execute(Player player, Votes plugin, String[] args) {
        player.sendMessage("Executing fake vote!");
        Vote vote = new Vote(plugin.getName(), player.getName(), player.getAddress().getHostString(), new Date().toString());
        Bukkit.getPluginManager().callEvent(new VotifierEvent(vote));
    }
}
