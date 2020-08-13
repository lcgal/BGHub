package com.example.bghub.Repositories.Data;

import android.content.Context;
import android.location.Location;

import com.example.bghub.Models.GameRooms.GameRoom;
import com.example.bghub.Models.Games.Game;
import com.example.bghub.Models.Session.Profile;
import com.example.bghub.Models.Session.Session;
import com.example.bghub.Models.UserLocation;
import com.facebook.AccessToken;

import org.json.JSONObject;

import java.util.Collection;
import java.util.List;

public interface DataContract {
    interface Repository {
        public AccessToken getLoginToken();

        void saveLoginToken(AccessToken loginToken);

        void startSession();

        Session getSession();

        void endSession();

        List<Game> getGamesList();

        void updateGameDescription (long gameid, String description);

        List<GameRoom> getGameRooms();

        List<Game> getGamesByIds(Collection<Long> gameIds);

        Game getGameById(long gameId);

        void saveGamesList(List<Game> gamesList, String version);

        void saveGameRooms(List<GameRoom> gameRooms);

        void insertGameRoom (GameRoom gameRoom);

        String getGamesListVersion();

        void updateLocation(Context context);

        Location getLocation();

        UserLocation getUserLocation();

        void saveProfile (Profile profile);
    }

}
