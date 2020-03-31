package com.example.bghub.Views.LoginView;

import com.facebook.AccessToken;
import com.facebook.Profile;

public interface LoginContract {
    interface View {


    }

    interface Presenter {

        //Saves the login information on DataRepository
        public void processUserLogin (Profile loginProfile, AccessToken loginToken);


    }
}
