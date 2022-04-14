package com.example.bghub.data.repositories.games

import com.example.bghub.data.models.Games.GameWithChildren
import com.example.bghub.data.services.data.DbContract
import javax.inject.Inject

class GamesRepository : GamesContract {
    @Inject
    lateinit var dataService : DbContract

    override fun saveGameList(games: List<GameWithChildren?>, version : String) {
        dataService.saveGamesList(games, version)
    }
}