package com.example.bghub.data.services.data;

import android.content.Context;
import android.location.Location;

import com.example.bghub.data.models.GameRooms.GameRoom;
import com.example.bghub.data.models.Games.Game;
import com.example.bghub.data.models.Games.GameWithChildren;
import com.example.bghub.data.models.users.User;
import com.example.bghub.data.models.users.UserLocation;
import java.util.Collection;
import java.util.List;

public interface DbContract {
        void saveUserInfo(User user);

        User getDbUserInfo(User user);

        void processLogin();

        List<GameWithChildren> getGamesList();

        void updateGameDescription (long gameid, String description);

        List<GameRoom> getGameRooms();

        List<Game> getGamesByIds(Collection<Long> gameIds);

        GameWithChildren getGameById(long gameId);

        void saveGamesList(List<GameWithChildren> gamesList, String version);

        void saveGameRooms(List<GameRoom> gameRooms);

        void insertGameRoom (GameRoom gameRoom);

        String getGamesListVersion();

        void updateLocation(Context context);

        Location getLocation();

        UserLocation getUserLocation();
}
