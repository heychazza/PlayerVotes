package com.codeitforyou.votes.command.sub;

import com.codeitforyou.lib.api.command.Command;
import com.codeitforyou.lib.api.general.StringUtil;
import com.codeitforyou.votes.Votes;
import org.bukkit.command.CommandSender;

public class ReloadCommand {
    @Command(aliases = {"reload"}, about = "Reload plugin configuration.", permission = "cifyvotes.reload", usage = "reload", title = "Reload Plugin")
    public static void execute(final CommandSender sender, final Votes plugin, final String[] args) {
        plugin.reloadConfig();
        plugin.register();

        final int rewardCount = plugin.getRewardManager().getRewards().size();
        sender.sendMessage(StringUtil.translate("&b[&l" + "Vote" + "&b] &7Successfully reloaded with &b" + rewardCount + " &7" + (rewardCount == 1 ? "reward" : "rewards") + " &7loaded."));
    }
}
