package com.example.bghub.Models;

import com.raizlabs.android.dbflow.annotation.Table;

public class Session {

    private int status;

    private Profile profile;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Profile getProfile() {
        return profile;
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
    }


}
