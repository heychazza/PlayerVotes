package com.codeitforyou.votes.listener;

import com.codeitforyou.votes.Votes;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

public class VoteListener implements Listener {

    private Votes plugin = Votes.getPlugin();

//    @EventHandler(priority = EventPriority.NORMAL)
//    public void onVoteEvent(VotifierEvent event) {
//        Vote vote = event.getVote();
//
//
//
//        /*
//         * Process Vote record as you see fit
//         */
//    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        ScriptEngine engine = new ScriptEngineManager().getEngineByName("nashorn");
        try {
            String input = "%player_displayname% == \"Siri\"";
            input = PlaceholderAPI.setPlaceholders(e.getPlayer(), input);
            plugin.getLogger().info("Result: " + engine.eval(input));
        } catch (ScriptException ex) {
            plugin.getLogger().warning("Failed to test script, reason: " + ex.getLocalizedMessage());
        }
    }
}
