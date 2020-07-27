package com.example.bghub.Views.Main;

import com.example.bghub.Models.Games.Game;
import com.example.bghub.Views.Fragments.OfferGameFragment;

import java.util.List;

public interface MainContract {
    interface View {


    }

    interface Presenter {

        void start();

        void logout();

        List<Game> getGames();

        public OfferGameFragment provideOfferGameFragment();


    }
}
