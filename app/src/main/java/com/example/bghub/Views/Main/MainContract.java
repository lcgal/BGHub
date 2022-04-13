package com.example.bghub.Views.Main;

import com.example.bghub.Models.Games.Game;
import com.example.bghub.Views.Fragments.OfferGameFragment;
import com.example.bghub.Views.Fragments.ProfileFragment;
import com.example.bghub.Views.Fragments.SearchGameFragment;

import java.util.List;

public interface MainContract {
    interface View {

        void openOfferGameFragment();

        void openSearchGameFragment();



    }

    interface Presenter {

        void start();

        List<Game> getGames();

        OfferGameFragment provideOfferGameFragment();

        SearchGameFragment provideSearchGameFragment();

        ProfileFragment provideProfileFragment();
    }
}
