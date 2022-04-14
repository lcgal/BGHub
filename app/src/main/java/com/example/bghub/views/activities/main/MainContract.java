package com.example.bghub.views.activities.main;

import com.example.bghub.data.models.Games.GameWithChildren;
import com.example.bghub.views.fragments.OfferGameFragment;
import com.example.bghub.views.fragments.ProfileFragment;
import com.example.bghub.views.fragments.SearchGameFragment;

import java.util.List;

public interface MainContract {
    interface View {

        void openOfferGameFragment();

        void openSearchGameFragment();



    }

    interface Presenter {

        void start();

        List<GameWithChildren> getGames();

        OfferGameFragment provideOfferGameFragment();

        SearchGameFragment provideSearchGameFragment();

        ProfileFragment provideProfileFragment();
    }
}
