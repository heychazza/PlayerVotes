package com.codeitforyou.votes.requirements;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import pw.valaria.requirementsprocessor.RequirementsProcessor;

import java.util.concurrent.ThreadLocalRandom;

public class HasChanceRequirement implements RequirementsProcessor {
    @Override
    public boolean checkMatch(@NotNull final Player player, @NotNull final ConfigurationSection configurationSection) {
        return ThreadLocalRandom.current().nextInt(configurationSection.getInt("chance", 10)) == 0;
    }
}
