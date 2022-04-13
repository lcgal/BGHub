package com.example.bghub.views.activities.main;

import com.example.bghub.data.models.Games.Game;
import com.example.bghub.data.services.data.DbContract;
import com.example.bghub.data.services.Http.HttpContract;
import com.example.bghub.views.fragments.OfferGameFragment;
import com.example.bghub.views.fragments.ProfileFragment;
import com.example.bghub.views.fragments.SearchGameFragment;

import java.util.List;

import javax.inject.Inject;

public class MainPresenter implements MainContract.Presenter {

    private MainContract.View _view;
    private final DbContract.Repository mDataRepository;
    private final HttpContract mHttpRepository;

    @Inject
    public MainPresenter(
            MainContract.View view,
            DbContract.Repository dataRepository,
            HttpContract httpRepository){

        _view = view;
        mDataRepository = dataRepository;
        mHttpRepository = httpRepository;
    }

    @Override
    public void start(){

    }

    @Override
    public List<Game> getGames() {
        return mDataRepository.getGamesList();
    }

    @Override
    public OfferGameFragment provideOfferGameFragment() {
        return OfferGameFragment.newInstance(mDataRepository, mHttpRepository);
    }

    @Override
    public SearchGameFragment provideSearchGameFragment() {
        return SearchGameFragment.newInstance(mDataRepository, mHttpRepository);
    }

    @Override
    public ProfileFragment provideProfileFragment() {
        return ProfileFragment.newInstance(mDataRepository);
    }

}

