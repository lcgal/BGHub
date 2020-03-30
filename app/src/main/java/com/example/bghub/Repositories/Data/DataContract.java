package com.example.bghub.Repositories.Data;

import com.facebook.AccessToken;
import com.facebook.Profile;

public interface DataContract {

    public AccessToken getLoginToken();

    public void saveLoginToken(AccessToken loginToken);

    public Profile getCurrentProfile();

    public void saveCurrentProfile(Profile loginProfile);

}
