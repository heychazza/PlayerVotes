package com.codeitforyou.votes.storage;

import com.codeitforyou.votes.storage.object.VoteHistory;

import java.util.UUID;

public interface StorageType {
    void pullUser(UUID target);
    void pushUser(UUID target);
    void pushHistory(UUID target, VoteHistory history);
}
