package com.example.bghub.Views.LoginView;

import com.example.bghub.Repositories.Data.DataContract;
import com.example.bghub.Repositories.Data.DataRepository;
import com.facebook.AccessToken;
import com.facebook.Profile;

import javax.inject.Inject;

public class LoginPresenter implements LoginContract.Presenter {

    private LoginContract.View mView;
    private DataContract mDataRepository;

    @Inject
    public LoginPresenter (
            LoginContract.View view,
            DataContract dataRepository){

        mView = view;
        mDataRepository = dataRepository;
    }

    @Override
    public void processUserLogin (Profile loginProfile, AccessToken loginToken){
        mDataRepository.saveCurrentProfile(loginProfile);
        mDataRepository.saveLoginToken(loginToken);


    }

}
