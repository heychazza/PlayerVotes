package com.codeitforyou.votes.manager;

import com.codeitforyou.votes.Votes;
import com.codeitforyou.votes.api.Reward;
import com.codeitforyou.votes.api.RewardMapper;
import com.codeitforyou.votes.storage.util.VoteUser;
import com.vexsoftware.votifier.model.Vote;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

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
        rewards.stream().filter(reward -> reward.canReward(player)).forEach(reward -> {
            List<String> actions = reward.getActions();
            Collections.replaceAll(actions, "%service%", vote.getServiceName());
            Collections.replaceAll(actions, "%timestamp%", vote.getTimeStamp());
            Collections.replaceAll(actions, "%votes%", String.valueOf(voteUser.getVotes()));
            reward.runActions(player);
        });
    }

    public List<Reward> getRewards() {
        return rewards;
    }
}
