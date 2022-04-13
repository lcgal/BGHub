package com.example.bghub.models.ApiResponse;

import com.example.bghub.models.GameRooms.GameRoom;

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
