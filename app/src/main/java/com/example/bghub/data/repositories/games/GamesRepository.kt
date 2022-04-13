package com.example.bghub.data.repositories.games

import com.example.bghub.data.models.Games.Game
import com.example.bghub.data.services.data.DbContract

class GamesRepository : GamesContract {
    lateinit var dataService : DbContract

    override fun saveGameList(games: List<Game?>, version : String) {
        dataService.saveGamesList(games, version)
    }
}