package com.example.bghub.Views.Main;

import com.example.bghub.Repositories.Data.DataContract;
import com.facebook.AccessToken;
import com.facebook.Profile;

import javax.inject.Inject;

public class MainPresenter implements MainContract.Presenter {

    private MainContract.View mView;
    private DataContract.Repository mDataRepository;

    @Inject
    public MainPresenter(
            MainContract.View view,
            DataContract.Repository dataRepository){

        mView = view;
        mDataRepository = dataRepository;
    }

}

