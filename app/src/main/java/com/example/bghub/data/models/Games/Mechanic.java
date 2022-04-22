package com.example.bghub.data.models.Games;

import static androidx.room.ForeignKey.CASCADE;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(tableName = "mechanics",
        foreignKeys = @ForeignKey(
                entity = Game.class,
                parentColumns = "id",
                childColumns = "gameId",
                onDelete = CASCADE),
        indices = @Index("gameId"))
public class Mechanic {

    @PrimaryKey
    @NonNull
    String id;

    @NonNull
    long gameId;

    @NonNull
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Mechanic mechanic = (Mechanic) o;
        return id.equals(mechanic.id) &&
                gameId == mechanic.gameId &&
                mechanic.equals(mechanic.mechanic);
    }
}
