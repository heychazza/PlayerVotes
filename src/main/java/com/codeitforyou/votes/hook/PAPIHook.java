package com.codeitforyou.votes.hook;

import com.codeitforyou.votes.Votes;
import com.codeitforyou.votes.storage.VoteUser;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.OfflinePlayer;

public class PAPIHook extends PlaceholderExpansion {
    private Votes plugin = Votes.getPlugin();

    public PAPIHook() {
        if (plugin.getServer().getPluginManager().getPlugin("PlaceholderAPI") != null) {
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
        VoteUser voteUser = plugin.getUserManager().getUser(player.getUniqueId());
        if(voteUser == null) {
            plugin.getLogger().warning("Failed to load player data for " + player.getName() + "!");
            plugin.getLogger().warning("Perhaps you reloaded using plugman?");
            return "";
        }
        if (placeholder.equalsIgnoreCase("votes")) {
            return String.valueOf(voteUser.getVotes());
        }

        return "Unknown Placeholder";
    }
}
