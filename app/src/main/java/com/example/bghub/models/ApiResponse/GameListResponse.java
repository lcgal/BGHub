package com.example.bghub.models.ApiResponse;

import com.example.bghub.models.Games.Game;

import java.util.List;

public class GameListResponse {

    private boolean Update;

    private String Version;

    private List<Game> Data;

    public List<Game> getData() {
        return Data;
    }

    public void setData(List<Game> result) {
        this.Data = result;
    }

    public boolean isUpdate() {
        return Update;
    }

    public void setUpdate(boolean update) {
        this.Update = update;
    }

    public String getVersion() {
        return Version;
    }

    public void setVersion(String version) {
        this.Version = version;
    }
}
