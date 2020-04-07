package com.codeitforyou.votes.manager;

import com.codeitforyou.votes.Votes;
import com.codeitforyou.votes.api.Reward;
import com.codeitforyou.votes.api.RewardMapper;
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
        this.rewards = new ArrayList<>();
    }

    public void loadRewards() {
        FileConfiguration config = plugin.getConfig();
        ConfigurationSection rewardsSection = config.getConfigurationSection("rewards");

        if (rewardsSection != null) {
            rewardsSection.getKeys(false).forEach(rewardId -> rewards.add(RewardMapper.mapReward(rewardId, Objects.requireNonNull(rewardsSection.getConfigurationSection(rewardId)))));
        }
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    public void executeVote(Player player, Vote vote) {
        rewards.stream().filter(reward -> reward.canReward(player)).forEach(reward -> {
            List<String> actions = reward.getActions();
            Collections.replaceAll(actions, "%service%", vote.getServiceName());
            Collections.replaceAll(actions, "%timestamp%", vote.getTimeStamp());
            Collections.replaceAll(actions, "%votes%", "0"); // TODO: make this not hard coded.
            reward.runActions(player);
        });
    }
}
