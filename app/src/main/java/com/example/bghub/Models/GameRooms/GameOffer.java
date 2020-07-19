package com.example.bghub.Models.GameRooms;

public class GameOffer {
    private long GameId;

    private double Latitude;

    private double Longitude;

    private String UserId;

    public GameOffer(Long gameId, double latitude, double longitude, String userId){
        GameId = gameId;
        Latitude = latitude;
        Longitude = longitude;
        UserId = userId;
    }

    public long getGameId() {
        return GameId;
    }

    public void setGameId(long gameId) {
        this.GameId = gameId;
    }

    public double getLatitude() {
        return Latitude;
    }

    public void setLatitude(double latitude) {
        this.Latitude = latitude;
    }

    public double getLongitude() {
        return Longitude;
    }

    public void setLongitude(double longitude) {
        this.Longitude = longitude;
    }

    public String getUserId() {
        return UserId;
    }

    public void setUserId(String userId) {
        this.UserId = userId;
    }

}
