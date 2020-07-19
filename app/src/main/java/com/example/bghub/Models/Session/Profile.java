package com.example.bghub.Models.Session;

import com.example.bghub.Models.AppDatabase;
import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.ForeignKey;
import com.raizlabs.android.dbflow.annotation.ForeignKeyReference;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;

@Table(database = AppDatabase.class)
public class Profile extends BaseModel {
    @Column
    @PrimaryKey
    private String UserId;

//    @ForeignKey(saveForeignKeyModel = false,
//            references = {@ForeignKeyReference(columnName = "UserId", foreignKeyColumnName = "UserId")})
    @ForeignKey(saveForeignKeyModel = true)
    private Credentials Credentials;

//    @ForeignKey(saveForeignKeyModel = false,
//            references = {@ForeignKeyReference(columnName = "UserId", foreignKeyColumnName = "UserId")})
    @ForeignKey(saveForeignKeyModel = true)
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

    public com.example.bghub.Models.Session.User getUser() {
        return User;
    }

    public void setUser(User user) {
        this.User = user;
    }

}
