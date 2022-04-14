package com.example.bghub.data.models.Games

import androidx.annotation.NonNull
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Relation

@Entity
data class Game (
    @PrimaryKey
    @NonNull
    var id: Long = 0,

    var name: String = "",

    var minPlayers: String = "",

    var maxPlayers: String = "",

    var thumbnail: String = "",

    var image: String = "",

    var description: String = "",
)

data class GameWithChildren (
    @Embedded
    val game : Game,

    @Relation(parentColumn = "id", entityColumn = "gameId")
    val mechanics : List<Mechanic>?,

    @Relation(parentColumn = "id", entityColumn = "gameId")
    val families : List<Family>?
) {

    fun getId() : Long = game.id
    fun getName() : String = game.name
    fun getMinPlayers() : String = game.minPlayers
    fun getMaxPlayers() : String = game.maxPlayers
    fun getThumbnail() : String = game.thumbnail
    fun getImage() : String = game.image
    fun getDescription() : String = game.description
}

//data class game (
//    var id: Long = 0,
//
//    var name: String = "",
//
//    var minPlayers: String = "",
//
//    var maxPlayers: String = "",
//
//    var thumbnail: String = "",
//
//    var image: String = "",
//
//    var description: String = "",
//
//    var mechanics: List<Mechanic>? = null,
//
//    var families: List<Family>? = null,
//)
