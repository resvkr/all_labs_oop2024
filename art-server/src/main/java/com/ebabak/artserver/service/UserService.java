package com.ebabak.artserver.service;

import com.ebabak.artserver.entity.User;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class UserService {
    private final Set<User> users = new HashSet<>();
    private final static long FIVE_MINUTES_IN_MILLS = 60 * 1000;


    public User register() {
        User e = new User();
        users.add(e);
        return e;
    }

    public Set<Integer> getTokens() {
        Set<Integer> tokens = new HashSet<>();
        for (User user : users) {
            tokens.add(user.getToken());
        }
        return tokens;
    }

    public boolean isUserActive(int token) {
        User user = getUserByToken(token);
        if (user != null) {
            long currentTime = System.currentTimeMillis();

            if (currentTime - user.getTime() > FIVE_MINUTES_IN_MILLS) {
                user.updateTimeAndStatus();
            }
            return user.canDraw();
        }
        return false;
    }

    public void restrictDrawingForUser(int token) {
        User user = getUserByToken(token);
        if (user != null) {
            user.setCanDraw(false);
        }
    }

    private User getUserByToken(int token) {
        for (User user : users) {
            if (user.getToken() == token) {
                return user;
            }
        }
        return null;
    }
}
