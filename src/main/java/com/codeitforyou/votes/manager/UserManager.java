package com.codeitforyou.votes.manager;

import com.codeitforyou.votes.storage.object.VoteUser;
import com.google.common.collect.Maps;

import java.util.Map;
import java.util.UUID;

public class UserManager {

    private Map<UUID, VoteUser> voteUserMap;

    public UserManager() {
        voteUserMap = Maps.newConcurrentMap();
    }

    public VoteUser getUser(UUID target) {
        if (!voteUserMap.containsKey(target)) {
            VoteUser voteUser = new VoteUser(target, 0);
            voteUserMap.put(target, voteUser);
        }

        return voteUserMap.get(target);
    }

    public void addUser(VoteUser voteUser) {
        getUsers().put(voteUser.getVoter(), voteUser);
    }

    public void removeUser(UUID target) {
        getUsers().remove(target);
    }

    public Map<UUID, VoteUser> getUsers() {
        return voteUserMap;
    }
}
