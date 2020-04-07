package com.codeitforyou.votes.listener;

import com.codeitforyou.votes.Votes;
import com.vexsoftware.votifier.model.Vote;
import com.vexsoftware.votifier.model.VotifierEvent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

public class VoteListener implements Listener {

    private Votes plugin = Votes.getPlugin();

    @EventHandler(priority = EventPriority.NORMAL)
    public void onVoteEvent(VotifierEvent event) {
        Vote vote = event.getVote();
        Player player = Bukkit.getPlayer(vote.getUsername());

        if (player != null) {
            plugin.getLogger().info("[DEBUG] Handling vote to service " + vote.getServiceName() + " by " + player.getName() + "..");
            plugin.getRewardManager().executeVote(player, vote);
        }
    }
}
