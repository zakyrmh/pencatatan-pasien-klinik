package com.clinic.utils;

import com.clinic.model.User;

public class Session {
    private static Session instance = null;
    private User loggedUser;

    private Session() {}

    public static Session getInstance() {
        if (instance == null) {
            instance = new Session();
        }
        return instance;
    }

    public void setUser(User user) {
        this.loggedUser = user;
    }

    public User getUser() {
        return loggedUser;
    }

    public void clear() {
        loggedUser = null;
    }
}
