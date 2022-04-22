package com.example.bghub.data.models.GameRooms;

//@Entity
public class GameRoom {

//    @PrimaryKey
//    @NonNull
    String Id;

    public com.example.bghub.data.models.Games.Game getGame() {
        return Game;
    }

    public void setGame(com.example.bghub.data.models.Games.Game game) {
        Game = game;
    }

    long GameId;

    double Latitude;

    double Longitude;

    float Distance;

    String HostId;

    int Status;

    com.example.bghub.data.models.Games.Game Game;

    public GameRoom(){
    }

    public GameRoom(String id, double latitude, double longitude, String userId, Long gameId, com.example.bghub.data.models.Games.Game game){
        Id = id;
        Latitude = latitude;
        Longitude = longitude;
        HostId = userId;
        Status = 1;
        GameId = gameId;
        Game = game;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public double getLatitude() {
        return Latitude;
    }

    public void setLatitude(double latitude) {
        Latitude = latitude;
    }

    public double getLongitude() {
        return Longitude;
    }

    public void setLongitude(double longitude) {
        Longitude = longitude;
    }

    public String getHostId() {
        return HostId;
    }

    public void setHostId(String hostId) {
        HostId = hostId;
    }

    public int getStatus() {
        return Status;
    }

    public void setStatus(int status) {
        Status = status;
    }

    public float getDistance() {
        return Distance;
    }

    public void setDistance(float distance) {
        Distance = distance;
    }

    public long getGameId() {
        return GameId;
    }

    public void setGameId(long gameId) {
        GameId = gameId;
    }

}
