package com.codeitforyou.votes.api;

import org.bukkit.configuration.ConfigurationSection;

import javax.security.auth.login.Configuration;
import java.util.ArrayList;
import java.util.List;

public class RewardMapper {

    public static Reward mapReward(String id, ConfigurationSection reward) {
        List<ConfigurationSection> requirements = new ArrayList<>();
        ConfigurationSection requirementSection = reward.getConfigurationSection("requirements");

        if (requirementSection != null)
            requirementSection.getKeys(false).forEach(requirement -> requirements.add(requirementSection.getConfigurationSection(requirement)));

        return new Reward(id, requirements, reward.getStringList("actions"));
    }
}
