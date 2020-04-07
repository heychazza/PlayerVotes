package com.codeitforyou.votes.storage.util;

public class VoteUser {

    @Column(key = "username")
    private String username;

    @Column(key = "votes")
    private int votes;

    public String getUsername() {
        return username;
    }

    public int getVotes() {
        return votes;
    }
}
