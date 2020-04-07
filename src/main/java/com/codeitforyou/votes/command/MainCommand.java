package com.codeitforyou.votes.command;

import com.codeitforyou.lib.api.command.Command;
import com.codeitforyou.votes.Votes;
import com.vexsoftware.votifier.model.Vote;
import com.vexsoftware.votifier.model.VotifierEvent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import sun.awt.CausedFocusEvent;

import java.util.Date;

public class MainCommand {

    @Command(title = "Vote")
    public static void execute(Player player, Votes plugin, String[] args) {
        player.sendMessage("Executing fake vote!");

        plugin.mySQLMapper.loadUser();
        Vote vote = new Vote(plugin.getName(), player.getName(), player.getAddress().getHostString(), new Date().toString());
        Bukkit.getPluginManager().callEvent(new VotifierEvent(vote));
    }
}
