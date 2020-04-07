package com.codeitforyou.votes.storage.util;

import com.codeitforyou.votes.storage.util.Column;

public class VoteUser {

    @Column(key = "username")
    public String username;

    @Column(key = "votes")
    public int votes;

}
