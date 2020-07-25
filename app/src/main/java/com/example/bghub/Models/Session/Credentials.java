package com.example.bghub.Models.Session;

import com.example.bghub.Models.AppDatabase;
import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;

import java.io.Serializable;

@Table(database = AppDatabase.class)
public class Credentials extends BaseModel implements Serializable {
    @Column
    @PrimaryKey
    private String UserId;

    @Column
    private String Usrname;

    @Column
    private String Psw;

    @Column
    private String FbId;

    public String getUserId() {
        return UserId;
    }

    public void setUserId(String userId) {
        UserId = userId;
    }

    public String getUsrname() {
        return Usrname;
    }

    public void setUsrname(String usrname) {
        Usrname = usrname;
    }

    public String getPsw() {
        return Psw;
    }

    public void setPsw(String psw) {
        Psw = psw;
    }

    public String getFbId() {
        return FbId;
    }

    public void setFbId(String fbId) {
        FbId = fbId;
    }


}
