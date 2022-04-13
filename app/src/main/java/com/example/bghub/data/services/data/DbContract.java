package com.example.bghub.data.services.data;

import android.content.Context;
import android.location.Location;

import com.example.bghub.data.models.GameRooms.GameRoom;
import com.example.bghub.data.models.Games.Game;
import com.example.bghub.data.models.User;
import com.example.bghub.data.models.UserLocation;
import java.util.Collection;
import java.util.List;

public interface DbContract {
    interface Repository {
        void saveUserInfo(User user);

        User getDbUserInfo(User user);

        void processLogin();

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
    }

}
