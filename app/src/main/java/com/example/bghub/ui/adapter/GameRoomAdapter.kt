package com.example.bghub.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.bghub.Models.GameRooms.GameRoom
import com.example.bghub.Models.Games.Game
import com.example.bghub.R

class GameRoomAdapter(private val gameRoomList: List<GameRoom>)
    : RecyclerView.Adapter<GameRoomAdapter.GameRoomHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GameRoomHolder {
        val inflater = LayoutInflater.from(parent.context)
        return GameRoomHolder(inflater, parent)
    }

    override fun getItemCount(): Int {
        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(holder: GameRoomHolder, position: Int) {
        TODO("Not yet implemented")
    }

    inner class GameRoomHolder(inflater: LayoutInflater, parent: ViewGroup) :
            RecyclerView.ViewHolder(inflater.inflate(R.layout.game_row, parent, false))  {

    }



}