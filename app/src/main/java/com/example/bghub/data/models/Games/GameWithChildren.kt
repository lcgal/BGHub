package com.example.bghub.data.models.Games

import androidx.room.Embedded
import androidx.room.Relation

data class GameWithChildren (
    @Embedded
    val game : Game,

    @Relation(parentColumn = "id", entityColumn = "gameId")
    val mechanics : List<Mechanic>?,

    @Relation(parentColumn = "id", entityColumn = "gameId")
    val families : List<Family>?
)