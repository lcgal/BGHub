package com.example.bghub.Models.Games;

import com.example.bghub.Models.AppDatabase;
import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;

@Table(database = AppDatabase.class)
public class Mechanic extends BaseModel {

    @PrimaryKey
    String Id ;

    @Column
    long GameId;

    @Column
    String Mechanic;

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public long getGameId() {
        return GameId;
    }

    public void setGameId(long gameId) {
        this.GameId = gameId;
    }

    public String getMechanic() {
        return Mechanic;
    }

    public void setMechanic(String mechanic) {
        this.Mechanic = mechanic;
    }
}
