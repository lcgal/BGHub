package com.example.bghub.Models;

import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;

public class Profile {
    @Column
    @PrimaryKey
    private String UserId;
    
    private Credentials Credentials;

    private User User;

    public String getUserId() {
        return UserId;
    }

    public void setUserId(String userId) {
        UserId = userId;
    }

    public Credentials getCredentials() {
        return Credentials;
    }

    public void setCredentials(Credentials credentials) {
        this.Credentials = credentials;
    }

    public User getUser() {
        return User;
    }

    public void setUser(User user) {
        this.User = user;
    }

}
