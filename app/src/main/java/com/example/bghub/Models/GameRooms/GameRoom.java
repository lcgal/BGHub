package com.example.bghub.Models.GameRooms;

import com.example.bghub.Models.AppDatabase;
import com.example.bghub.Models.Games.Game;
import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.ForeignKey;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;

@Table(database = AppDatabase.class)
public class GameRoom extends BaseModel {

    @Column
    @PrimaryKey
    String Id;

    public com.example.bghub.Models.Games.Game getGame() {
        return Game;
    }

    public void setGame(com.example.bghub.Models.Games.Game game) {
        Game = game;
    }

    @Column
    long GameId;

    @Column
    double Latitude;

    @Column
    double Longitude;

    @Column
    float Distance;

    @Column
    String HostId;

    @Column
    int Status;

    @ForeignKey(saveForeignKeyModel = false)
    Game Game;

    public GameRoom(){
    }

    public GameRoom(String id, double latitude, double longitude, String userId, Long gameId, Game game){
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
