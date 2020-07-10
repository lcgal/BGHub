package com.example.bghub.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.bghub.Models.Games.Game
import com.example.bghub.ui.Holder.GameListHolder

class GameListAdapter(private val list: List<Game>)
    : RecyclerView.Adapter<GameListHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GameListHolder {
        val inflater = LayoutInflater.from(parent.context)
        return GameListHolder(inflater, parent)
    }

    override fun onBindViewHolder(holder: GameListHolder, position: Int) {
        val game: Game = list[position]
        holder.bind(game)
    }

    override fun getItemCount(): Int = list.size

}