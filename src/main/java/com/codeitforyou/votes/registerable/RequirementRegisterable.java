package com.codeitforyou.votes.registerable;

import com.codeitforyou.votes.requirements.HasChanceRequirement;
import pw.valaria.requirementsprocessor.RequirementsUtil;

public class RequirementRegisterable {
    public static void register() {
        RequirementsUtil.registerProcessor("HAS_CHANCE", new HasChanceRequirement());
    }
}
