package com.codeitforyou.votes.storage;

import java.util.UUID;

public class VoteUser {

    @Column(key = "uuid")
    private String voter;

    @Column(key = "votes")
    private int votes;

    public VoteUser() {}
    public VoteUser(UUID voter, int votes) {
        this.voter = voter.toString();
        this.votes = votes;
    }

    public UUID getVoter() {
        return UUID.fromString(voter);
    }

    public int getVotes() {
        return votes;
    }

    public void addVotes(int amount) {
        votes+=amount;
    }
    public void addVote() {
        addVotes(1);
    }
}
