package com.example.bghub.data

import com.example.bghub.data.models.Games.Game
import com.example.bghub.data.models.Games.GameEntityWithChildren
import com.example.bghub.data.models.mapList

inline fun map(input: GameEntityWithChildren): Game {
    return Game(input)
}

inline fun map(input: Game): GameEntityWithChildren {
    return GameEntityWithChildren(input)
}

inline fun map(input: List<Game>): List<GameEntityWithChildren> {
    return mapList(input) { input -> map(input) }
}

@JvmName("map1")
inline fun map(input: List<GameEntityWithChildren>): List<Game> {
    return mapList(input) { input -> map(input) }
}


