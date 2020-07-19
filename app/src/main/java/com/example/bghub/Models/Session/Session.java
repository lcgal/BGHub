package com.example.bghub.Models.Session;

import com.example.bghub.Models.Session.Profile;
import com.raizlabs.android.dbflow.annotation.ForeignKey;

public class Session {

    private int Status;

    private Profile Profile;


    public int getStatus() {
        return Status;
    }

    public void setStatus(int status) {
        //TODO create constants for SessionStatus
        this.Status = status;
    }

    public Profile getProfile() {
        return Profile;
    }

    public void setProfile(Profile profile) {
        this.Profile = profile;
    }


}
