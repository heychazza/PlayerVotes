package com.codeitforyou.votes.hook;

import com.codeitforyou.votes.Votes;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;

public class PAPIHook extends PlaceholderExpansion {
    private Votes plugin = Votes.getPlugin();

    public PAPIHook() {
        if(plugin.getServer().getPluginManager().getPlugin("PlaceholderAPI") != null) {
            this.register();
        }
    }

    @Override
    public String getIdentifier() {
        return plugin.getDescription().getName();
    }

    @Override
    public String getAuthor() {
        return plugin.getDescription().getAuthors().get(0);
    }

    @Override
    public String getVersion() {
        return plugin.getDescription().getVersion();
    }

    @SuppressWarnings("deprecation")
    @Override
    public String onRequest(final OfflinePlayer player, final String placeholder) {
        if(placeholder.equalsIgnoreCase("votes")) {
            return String.valueOf(Bukkit.getPlayer(player.getUniqueId()).getItemInHand().getAmount());
        }

        return "Unknown Placeholder";
    }
}
