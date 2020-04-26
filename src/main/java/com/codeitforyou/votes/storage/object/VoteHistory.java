package com.codeitforyou.votes.storage.object;

import com.codeitforyou.votes.storage.Column;

import java.util.UUID;

public class VoteHistory {

    @Column(key = "uuid")
    private String voter;

    @Column(key = "service")
    private String service;

    @Column(key = "time")
    private Long time;

    public VoteHistory() {
    }

    public VoteHistory(UUID voter, String service, long time) {
        this.voter = voter.toString();
        this.service = service;
        this.time = time;
    }

    public UUID getVoter() {
        return UUID.fromString(voter);
    }

    private String getService() {
        return service;
    }

    private Long getTime() {
        return time;
    }
}
