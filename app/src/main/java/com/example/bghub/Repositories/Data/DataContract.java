package com.example.bghub.Repositories.Data;

import android.content.Context;
import android.location.Location;

import com.example.bghub.Models.Games.Game;
import com.example.bghub.Models.Session.Profile;
import com.example.bghub.Models.Session.Session;
import com.facebook.AccessToken;

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

        public void updateLocation(Context context);

        public Location getLocation();

        public void saveProfile (Profile profile);
    }

}
