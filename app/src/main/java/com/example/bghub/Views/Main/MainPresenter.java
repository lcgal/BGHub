package com.example.bghub.Views.Main;

import com.example.bghub.Models.Games.Game;
import com.example.bghub.Repositories.Data.DataContract;
import com.example.bghub.Repositories.Http.HttpContract;
import com.example.bghub.Repositories.Http.HttpRepository;
import com.example.bghub.Views.Fragments.OfferGame.OfferGameFragment;
import com.facebook.AccessToken;
import com.facebook.Profile;

import java.util.List;

import javax.inject.Inject;

import static com.example.bghub.Commons.AppConstants.Logged_out;

public class MainPresenter implements MainContract.Presenter {

    private MainContract.View mView;
    private DataContract.Repository mDataRepository;
    private HttpContract mHttpRepository;

    @Inject
    public MainPresenter(
            MainContract.View view,
            DataContract.Repository dataRepository,
            HttpContract httpRepository){

        mView = view;
        mDataRepository = dataRepository;
        mHttpRepository = httpRepository;
    }

    @Override
    public void start(){

        if (mDataRepository.getGamesList() == null){
            mDataRepository.setGamesList();
        }

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
    public void logout(){
        mDataRepository.changeSessionStatus(Logged_out);
    }

}

