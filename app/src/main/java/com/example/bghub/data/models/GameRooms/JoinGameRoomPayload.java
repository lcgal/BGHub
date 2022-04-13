package com.example.bghub.data.models.GameRooms;

public class JoinGameRoomPayload {

    String GameRoomId;

    String UserId;

    public JoinGameRoomPayload(String roomId, String userId) {
        GameRoomId = roomId;
        UserId = userId;
    }

    public String getGameRoomId() {
        return GameRoomId;
    }

    public void setGameRoomId(String gameRoomId) {
        GameRoomId = gameRoomId;
    }

    public String getUserId() {
        return UserId;
    }

    public void setUserId(String userId) {
        UserId = userId;
    }

}
