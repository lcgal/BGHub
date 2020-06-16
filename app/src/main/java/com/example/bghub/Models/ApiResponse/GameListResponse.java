package com.example.bghub.Models.ApiResponse;

import com.example.bghub.Models.Games.Game;

import java.util.List;

public class GameListResponse {

    private boolean update;

    private String version;

    private List<Game> data;

    public List<Game> getData() {
        return data;
    }

    public void setData(List<Game> result) {
        this.data = result;
    }

    public boolean isUpdate() {
        return update;
    }

    public void setUpdate(boolean update) {
        this.update = update;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }
}
