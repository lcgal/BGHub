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

    @Column
    double Latitude;

    @Column
    double Longitude;

    @Column
    String HostId;

    @Column
    int Status;

    @Column
    long GameId;

    @ForeignKey(saveForeignKeyModel = true)
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
}
