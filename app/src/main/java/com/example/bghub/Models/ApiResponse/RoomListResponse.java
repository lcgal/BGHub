package com.example.bghub.Models.ApiResponse;

import com.example.bghub.Models.GameRooms.GameRoom;
import com.example.bghub.Models.Games.Game;

import java.util.List;

public class RoomListResponse {

    private List<GameRoom> GameRooms;

    private String Error;

    public List<GameRoom> getGameRooms() {
        return GameRooms;
    }

    public String getError() {
        return Error;
    }
}
