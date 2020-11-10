package com.example.bghub.Models.Session;

import com.example.bghub.Models.AppDatabase;
import com.example.bghub.Models.Session.Profile;
import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.ForeignKey;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;

import java.util.UUID;

import static com.example.bghub.Commons.AppConstants.Logged_in;
import static com.example.bghub.Commons.AppConstants.Processing_Login;

@Table(database = AppDatabase.class)
public class Session extends BaseModel {

    @Column
    @PrimaryKey
    private String SessionId;

    @Column
    private int Status;

    @ForeignKey(saveForeignKeyModel = true)
    private Profile Profile;

    public Session () {
        SessionId = UUID.randomUUID().toString();
        Status = Processing_Login;
    }


    public int getStatus() {
        return Status;
    }

    public void setStatus(int status) {
        this.Status = status;
    }

    public Profile getProfile() {
        return Profile;
    }

    public void setProfile(Profile profile) {
        this.Profile = profile;
    }

    public String getSessionId() {
        return SessionId;
    }

    public void setSessionId(String sessionId) {
        SessionId = sessionId;
    }


}
