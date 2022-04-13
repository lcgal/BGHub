package com.example.bghub.data.models.apiResponse;

import com.example.bghub.data.models.GameRooms.GameRoom;

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
