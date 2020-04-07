package com.codeitforyou.votes.storage.util;

import java.util.UUID;

public class VoteUser {

    @Column(key = "uuid")
    private String voter;

    @Column(key = "votes")
    private int votes;

    public UUID getVoter() {
        return UUID.fromString(voter);
    }

    public int getVotes() {
        return votes;
    }
}
