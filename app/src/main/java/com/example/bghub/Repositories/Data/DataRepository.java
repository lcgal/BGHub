package com.example.bghub.Repositories.Data;

import com.facebook.AccessToken;
import com.facebook.Profile;

public class DataRepository implements DataContract {

    public AccessToken loginToken;

    public Profile loginProfile;


    @Override
    public AccessToken getLoginToken() {
        return loginToken;
    }

    @Override
    public void saveLoginToken(AccessToken loginToken) {
        this.loginToken = loginToken;
    }

    @Override
    public Profile getCurrentProfile() {
        return loginProfile;
    }

    @Override
    public void saveCurrentProfile(Profile loginProfile) {
        this.loginProfile = loginProfile;
    }

}
