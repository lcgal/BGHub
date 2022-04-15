package com.example.bghub.data.repositories.games

import com.example.bghub.data.models.Games.Game

interface GamesContract {

    fun saveGameList(games: List<Game?>, version : String)

}