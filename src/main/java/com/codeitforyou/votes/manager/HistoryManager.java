package com.codeitforyou.votes.manager;

import com.codeitforyou.votes.Votes;
import com.codeitforyou.votes.storage.object.VoteHistory;
import com.google.common.collect.Maps;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.*;

public class HistoryManager {

    private Map<UUID, List<VoteHistory>> voteHistoryMap;

    public HistoryManager() {
        voteHistoryMap = Maps.newConcurrentMap();
    }

    /*
     - Every 10 minutes,
     > clone list
     > for every history, add to database
     > remove all at end
     */

    public void runAutoSave() {
        new BukkitRunnable() {
            @Override
            public void run() {
                for (Map.Entry<UUID, List<VoteHistory>> historyEntry : voteHistoryMap.entrySet()) {

                }
            }
        }.runTaskTimer(Votes.getPlugin(), (20 * 60) * 10, (20 * 60) * 10);
    }

    public List<VoteHistory> getHistory(UUID target) {
        if (!voteHistoryMap.containsKey(target)) voteHistoryMap.put(target, Collections.emptyList());
        return voteHistoryMap.get(target);
    }

    public void addHistory(UUID target, VoteHistory voteHistory) {
        getHistory(target).add(voteHistory);
    }

    public void removeHistory(UUID target, VoteHistory voteHistory) {
        getHistory(target).remove(voteHistory);
    }

    public Map<UUID, List<VoteHistory>> getHistory() {
        return voteHistoryMap;
    }
}
