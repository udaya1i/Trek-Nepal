package com.trek.trekker.service;

import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class PresenceService {

    private final Set<String> onlineUsernames = Collections.newSetFromMap(new ConcurrentHashMap<>());

    public void markOnline(String username) {
        onlineUsernames.add(username);
    }

    public void markOffline(String username) {
        onlineUsernames.remove(username);
    }

    public boolean isOnline(String username) {
        return onlineUsernames.contains(username);
    }
}