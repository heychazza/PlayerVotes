package com.codeitforyou.votes.manager;

import com.codeitforyou.votes.Votes;
import com.codeitforyou.votes.api.Reward;
import com.codeitforyou.votes.api.RewardMapper;
import com.codeitforyou.votes.storage.object.VoteUser;
import com.vexsoftware.votifier.model.Vote;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.*;

public class RewardManager {

    private Votes plugin;
    private List<Reward> rewards;

    public RewardManager(Votes plugin) {
        this.plugin = plugin;
    }

    public void loadRewards() {
        this.rewards = new ArrayList<>();
        FileConfiguration config = plugin.getConfig();
        ConfigurationSection rewardsSection = config.getConfigurationSection("rewards");

        if (rewardsSection != null) {
            rewardsSection.getKeys(false).forEach(rewardId -> rewards.add(RewardMapper.mapReward(rewardId, Objects.requireNonNull(rewardsSection.getConfigurationSection(rewardId)))));
        }
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    public void executeVote(Player player, Vote vote) {
        VoteUser voteUser = plugin.getUserManager().getUser(player.getUniqueId());
        rewards.stream().sorted(Comparator.comparing(Reward::getPriority).reversed()).filter(reward -> reward.canReward(player)).forEach(reward -> {
            plugin.getLogger().info("Found reward " + reward.getId() + " with priority " + reward.getPriority());
            List<String> actions = reward.getActions();
            Collections.replaceAll(actions, "%service%", vote.getServiceName());
            Collections.replaceAll(actions, "%timestamp%", vote.getTimeStamp());
            Collections.replaceAll(actions, "%votes%", String.valueOf(voteUser.getVotes()));
            Collections.replaceAll(actions, "%player%", player.getName());
            Collections.replaceAll(actions, "%uuid%", player.getUniqueId().toString());
            reward.runActions(player);
        });
    }

    public List<Reward> getRewards() {
        return rewards;
    }
}
