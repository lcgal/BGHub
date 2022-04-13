package com.example.bghub.data.models.Games;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Mechanic {

    @PrimaryKey
    String id;

    long gameId;

    String mechanic;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public long getGameId() {
        return gameId;
    }

    public void setGameId(long gameId) {
        this.gameId = gameId;
    }

    public String getMechanic() {
        return mechanic;
    }

    public void setMechanic(String mechanic) {
        this.mechanic = mechanic;
    }
}
