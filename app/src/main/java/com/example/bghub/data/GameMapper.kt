package com.example.bghub.data

import com.example.bghub.data.models.Games.Game
import com.example.bghub.data.models.Games.GameEntityWithChildren
import com.example.bghub.data.models.mapList

inline fun mapGameEntityToModel(input: GameEntityWithChildren): Game {
    return Game(input)
}

inline fun mapGameModelToEntity(input: Game): GameEntityWithChildren {
    return GameEntityWithChildren(input)
}

inline fun mapGameModelToEntity(input: List<Game>): List<GameEntityWithChildren> {
    val mapListItem: (Game) -> GameEntityWithChildren = { input -> mapGameModelToEntity(input) }
    return mapList(input, mapListItem)
}


