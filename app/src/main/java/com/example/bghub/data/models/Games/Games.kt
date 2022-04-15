import androidx.room.Embedded
import androidx.room.Relation
import com.example.bghub.data.models.Games.Family
import com.example.bghub.data.models.Games.GameEntity
import com.example.bghub.data.models.Games.Mechanic

//package com.example.bghub.data.models.Games
//
//import androidx.annotation.NonNull
//import androidx.room.*
//
//@Entity(tableName = "games")
//data class GameEntity (
//    @PrimaryKey
//    @NonNull
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
//)
//
//@Entity(
//    tableName = "mechanics",
//    foreignKeys = [ForeignKey(
//        entity = GameEntity::class,
//        parentColumns = ["id"],
//        childColumns = ["gameId"],
//        onDelete = ForeignKey.CASCADE
//    )],
//    indices = [Index("gameId")]
//)
//data class Mechanic (
//    @PrimaryKey
//    var id: String = "",
//    var gameId: Long = 0,
//    var mechanic: String? = null
//)
//
//@Entity(
//    tableName = "families",
//    foreignKeys = [ForeignKey(
//        entity = GameEntity::class,
//        parentColumns = ["id"],
//        childColumns = ["gameId"],
//        onDelete = ForeignKey.CASCADE
//    )],
//    indices = [Index("gameId")]
//)
//data class Family (
//    @PrimaryKey
//    var id: String = "",
//
//    var gameId: Long = 0,
//
//    var family: String? = null,
//)
//
//data class GameWithChildren (
//    @Embedded
//    val gameEntityEntity : GameEntity,
//
//    @Relation(parentColumn = "id", entityColumn = "gameId")
//    val mechanics : List<Mechanic>?,
//
//    @Relation(parentColumn = "id", entityColumn = "gameId")
//    val families : List<Family>?
//) {
//
//    fun getId(): Long = gameEntityEntity.id
//    fun getName(): String = gameEntityEntity.name
//    fun getMinPlayers(): String = gameEntityEntity.minPlayers
//    fun getMaxPlayers(): String = gameEntityEntity.maxPlayers
//    fun getThumbnail(): String = gameEntityEntity.thumbnail
//    fun getImage(): String = gameEntityEntity.image
//    fun getDescription(): String = gameEntityEntity.description
//}
//
//
//
//
////data class Game : GameEntity (
////
////        )
