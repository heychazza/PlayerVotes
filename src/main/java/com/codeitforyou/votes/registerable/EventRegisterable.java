package com.codeitforyou.votes.registerable;

import com.codeitforyou.votes.Votes;
import com.codeitforyou.votes.listener.JoinListener;
import com.codeitforyou.votes.listener.VoteListener;
import org.bukkit.event.Listener;

public class EventRegisterable {
    private static final Listener[] LISTENERS = {
            new VoteListener(),
            new JoinListener()
    };

    public static void register() {
        Votes plugin = Votes.getPlugin();
        for (Listener listener : LISTENERS) {
            plugin.getServer().getPluginManager().registerEvents(listener, plugin);
        }
    }
}
