package com.example.bghub.Repositories.Data;

import com.example.bghub.Models.Games.Game;
import com.example.bghub.Models.Session;
import com.facebook.AccessToken;
import com.facebook.Profile;

import java.util.List;

public interface DataContract {
    interface Repository {
        public AccessToken getLoginToken();

        void saveLoginToken(AccessToken loginToken);

        Session getSession();

        void saveSession(Session session);

        void changeSessionStatus (int status);

        List<Game> getGamesList();

        void saveGamesList(List<Game> gamesList, String version);

        String getGamesListVersion();

        void setGamesList();

    }

}
