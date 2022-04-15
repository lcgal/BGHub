package com.example.bghub.data.models.Games;

import java.util.List;
import java.util.Map;

public class Game extends GameEntity {
    public Game(){
    }

    public Game(long id,
                String name,
                String minPlayers,
                String maxPlayers,
                String thumbnail,
                String image,
                String description,
                List<Mechanic> mechanics,
                List<Family> families){
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
}
