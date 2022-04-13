package com.example.bghub.data.models.Games;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.List;
import java.util.Locale;

@Entity
public class Game {
    @PrimaryKey
    long id;

    String name;

    String minPlayers;

    String maxPlayers;

    String thumbnail;

    String image;

    String description;

    List<Mechanic> mechanics;

    List<Family> families;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMinPlayers() {
        return minPlayers;
    }

    public void setMinPlayers(String minPlayers) {
        this.minPlayers = minPlayers;
    }

    public String getMaxPlayers() {
        return maxPlayers;
    }

    public void setMaxPlayers(String maxPlayers) {
        this.maxPlayers = maxPlayers;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String toLowerCase(Locale locale) {return this.name.toLowerCase(locale);}

    public String getImage() {return image;}

    public void setImage(String image) {
        this.image = image;}

    public String getDescription() {return description;}

    public void setDescription(String description) {
        this.description = description;}
}
