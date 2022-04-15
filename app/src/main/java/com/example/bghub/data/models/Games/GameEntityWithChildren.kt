package com.example.bghub.data.models.Games

import androidx.room.Embedded
import androidx.room.Relation

data class GameEntityWithChildren (
    @Embedded
    val gameEntityEntity : GameEntity,

    @Relation(parentColumn = "id", entityColumn = "gameId")
    val mechanics : List<Mechanic>?,

    @Relation(parentColumn = "id", entityColumn = "gameId")
    val families : List<Family>?
) {
    constructor(model : Game) : this(model.gameEntity, model.mechanics, model.families)

    fun getId(): Long = gameEntityEntity.id
    fun getName(): String = gameEntityEntity.name
    fun getMinPlayers(): String = gameEntityEntity.minPlayers
    fun getMaxPlayers(): String = gameEntityEntity.maxPlayers
    fun getThumbnail(): String = gameEntityEntity.thumbnail
    fun getImage(): String = gameEntityEntity.image
    fun getDescription(): String = gameEntityEntity.description
}