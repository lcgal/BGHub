package com.example.bghub.Models.Games;

import com.example.bghub.Models.AppDatabase;
import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;

@Table(database = AppDatabase.class)
public class Family extends BaseModel {

    @PrimaryKey
    String Id ;

    @Column
    float gameId;

    @Column
    String family;

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public float getGameId() {
        return gameId;
    }

    public void setGameId(float gameId) {
        this.gameId = gameId;
    }

    public String getFamily() {
        return family;
    }

    public void setFamily(String family) {
        this.family = family;
    }
}
