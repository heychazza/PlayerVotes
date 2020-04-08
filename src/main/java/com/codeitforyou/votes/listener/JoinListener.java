package com.codeitforyou.votes.listener;

import com.codeitforyou.votes.Votes;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class JoinListener implements Listener {
    private final Votes plugin = Votes.getPlugin();

    @EventHandler(priority = EventPriority.NORMAL)
    public void onJoinEvent(PlayerJoinEvent e) {
        plugin.getStorageType().pullUser(e.getPlayer().getUniqueId());
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void onQuitEvent(PlayerQuitEvent e) {
        plugin.getStorageType().pushUser(e.getPlayer().getUniqueId());
    }
}
