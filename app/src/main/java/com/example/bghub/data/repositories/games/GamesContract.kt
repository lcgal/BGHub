package com.example.bghub.data.repositories.games

import com.example.bghub.data.models.Games.GameWithChildren

interface GamesContract {

    fun saveGameList(games: List<GameWithChildren?>, version : String)

}