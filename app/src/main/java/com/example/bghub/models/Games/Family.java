package com.example.bghub.models.Games;

import com.example.bghub.models.AppDatabase;
import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;

@Table(database = AppDatabase.class)
public class Family extends BaseModel {

    @PrimaryKey
    String Id ;

    @Column
    long GameId;

    @Column
    String Family;

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

    public String getFamily() {
        return Family;
    }

    public void setFamily(String family) {
        this.Family = family;
    }
}
