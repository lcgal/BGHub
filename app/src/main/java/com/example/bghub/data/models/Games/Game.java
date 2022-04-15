package com.example.bghub.data.models.Games;

import androidx.annotation.Nullable;

import java.util.List;
import java.util.Map;

public class Game extends GameEntity {
    public Game(){
    }

    public Game(long id,
                String name,
                @Nullable String minPlayers,
                @Nullable String maxPlayers,
                @Nullable String thumbnail,
                @Nullable String image,
                @Nullable String description,
                @Nullable List<Mechanic> mechanics,
                @Nullable List<Family> families){
        this.id = id;
        this.name = name;
        this.minPlayers = minPlayers;
        this.maxPlayers = maxPlayers;
        this.thumbnail = thumbnail;
        this.image = image;
        this.description = description;
        this.mechanics = mechanics;
        this.families = families;
    }

    public Game(GameEntityWithChildren entity){
        this.id = entity.getId();
        this.name = entity.getName();
        this.minPlayers = entity.getMinPlayers();
        this.maxPlayers = entity.getMaxPlayers();
        this.thumbnail = entity.getThumbnail();
        this.image = entity.getImage();
        this.description = entity.getDescription();
        this.mechanics = entity.getMechanics();
        this.families = entity.getFamilies();
    }

    public GameEntity getGameEntity() {
        GameEntity entity = new GameEntity();
        entity.id = id;
        entity.name = name;
        entity.minPlayers = minPlayers;
        entity.maxPlayers = maxPlayers;
        entity.thumbnail = thumbnail;
        entity.image = image;
        entity.description = description;
        return entity;
    }

    List<Mechanic> mechanics;

    List<Family> families;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Game game = (Game) o;
        return id == game.id &&
                name.equals(game.name) &&
                minPlayers == null ? game.minPlayers == null : minPlayers.equals(game.minPlayers) &&
                maxPlayers == null ? game.maxPlayers == null : maxPlayers.equals(game.maxPlayers) &&
                thumbnail == null ? game.thumbnail == null : thumbnail.equals(game.thumbnail) &&
                image == null ? game.image == null : image.equals(game.image) &&
                description == null ? game.description == null : description.equals(game.description) &&
                mechanics == null ? game.mechanics == null : mechanics.equals(game.mechanics) &&
                families == null ? game.families == null : families.equals(game.families);
    }
}
