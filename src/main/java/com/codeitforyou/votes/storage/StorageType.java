package com.codeitforyou.votes.storage;

import java.util.UUID;

public interface StorageType {
    void pullUser(UUID target);
    void pushUser(UUID target);
}