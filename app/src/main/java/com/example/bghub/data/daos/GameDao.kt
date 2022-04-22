package com.example.bghub.data.daos

import com.example.bghub.data.models.Games.GameWithChildren
import androidx.room.*
import com.example.bghub.data.models.Games.Family
import com.example.bghub.data.models.Games.Game
import com.example.bghub.data.models.Games.Mechanic

@Dao
interface GameDao {
    @Query(
        "SELECT * FROM games " +
                "JOIN mechanics on games.id = mechanics.gameId " +
                "JOIN families on games.id = families.gameId "
    )
    fun getGamesWithChildren(): List<GameWithChildren>

//    @Insert
//    fun insertGames(games: List<GameWithChildren>) {
//        games.forEach {insertGame(it)}
//    }
//
//    @Transaction
//    fun insertGame(games: GameWithChildren) {
//        insertGame(games.gameEntityEntity)
//        if (games.mechanics != null) {
//            insertMechanics(games.mechanics)
//        }
//        if (games.families != null) {
//            insertFamilies(games.families)
//        }
//    }

    @Insert
    fun insertGame(gameEntity: Game)

    @Insert
    fun insertMechanics(mechanics: List<Mechanic>)

    @Insert
    fun insertFamilies(families: List<Family>)

    @Delete
    fun deleteGame(gameEntity: Game)

    @Insert
    fun insertMechanic(vararg mechanic: Mechanic)

    @Delete
    fun deleteMechanic(mechanic: Mechanic)

    @Insert
    fun insertFamily(vararg family: Family)

    @Delete
    fun deleteFamily(family: Family)
}