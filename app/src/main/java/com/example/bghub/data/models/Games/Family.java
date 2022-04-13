package com.example.bghub.data.models.Games;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Family{

    @PrimaryKey
    String id;

    long gameId;

    String family;

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

    public String getFamily() {
        return family;
    }

    public void setFamily(String family) {
        this.family = family;
    }
}
