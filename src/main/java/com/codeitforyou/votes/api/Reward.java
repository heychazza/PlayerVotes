package com.codeitforyou.votes.api;

import com.codeitforyou.votes.Votes;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import pw.valaria.requirementsprocessor.RequirementsUtil;

import java.util.List;

public class Reward {

    private final String id;
    private final List<ConfigurationSection> requirements;
    private final List<String> actions;
    private final int priority;

    public Reward(String id, List<ConfigurationSection> requirements, List<String> actions, int priority) {
        this.id = id;
        this.requirements = requirements;
        this.actions = actions;
        this.priority = priority;
    }

    public String getId() {
        return id;
    }

    public List<ConfigurationSection> getRequirements() {
        return requirements;
    }

    public List<String> getActions() {
        return actions;
    }

    public int getPriority() {
        return priority;
    }

    public boolean canReward(Player player) {
        return requirements.stream().filter(item -> RequirementsUtil.handle(player, item)).count() == requirements.size();
    }

    public void runActions(Player player) {
        Votes.getPlugin().getActionManager().runActions(player, getActions());
    }
}
