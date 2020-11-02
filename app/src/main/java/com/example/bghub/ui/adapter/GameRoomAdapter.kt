package com.example.bghub.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.DrawableRes
import androidx.recyclerview.widget.RecyclerView
import com.example.bghub.Models.GameRooms.GameRoom
import com.example.bghub.Models.Games.Game
import com.example.bghub.R
import com.squareup.picasso.Picasso

class GameRoomAdapter(private val gameRoomList: List<GameRoom>, onGameClickListener: OnGameClickListener)
    : RecyclerView.Adapter<GameRoomAdapter.GameRoomHolder>() {

    var mOnGameClickListener: OnGameClickListener

    init {
        mOnGameClickListener = onGameClickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GameRoomHolder {
        val inflater = LayoutInflater.from(parent.context)
        return GameRoomHolder(inflater, parent, mOnGameClickListener)
    }

    override fun getItemCount(): Int {
        return gameRoomList.count()
    }

    override fun onBindViewHolder(holder: GameRoomHolder, position: Int) {
        val gameRoom: GameRoom = gameRoomList[position]
        holder.bind(gameRoom)
    }

    inner class GameRoomHolder(inflater: LayoutInflater, parent: ViewGroup, onGameClickListener: OnGameClickListener) :
            RecyclerView.ViewHolder(inflater.inflate(R.layout.card_view_game, parent, false))  {
        private var mImageView: ImageView? = null
        private var mNameView: TextView? = null
        private var mDistanceView: TextView? = null
        private var mJoinButton: ImageButton? = null
        private var mOnGameClickListener : OnGameClickListener

        init {
            mImageView = itemView.findViewById(R.id.game_image)
            mNameView = itemView.findViewById(R.id.game_name)
            mDistanceView = itemView.findViewById(R.id.distance)
            mJoinButton = itemView.findViewById(R.id.join_button)
            mOnGameClickListener = onGameClickListener
        }

        fun bind(gameRoom: GameRoom) {
            var game = gameRoom.game
            mImageView?.loadThumbnailInList(game.thumbnail)
            mNameView?.text = game.name
            mDistanceView?.text = gameRoom.distance.toString()

            itemView.setOnClickListener{
                mOnGameClickListener.OnGameClick(gameRoomList.get(adapterPosition).game)
            }

        }

        fun ImageView.loadThumbnailInList(imageUrl: String?, @DrawableRes errorResId: Int = R.drawable.thumbnail_image_empty) {
            Picasso.with(context)
                    .load(imageUrl)
                    .placeholder(errorResId)
                    .error(errorResId)
                    .fit()
                    .centerCrop()
                    .into(this)
        }
    }

    interface OnGameClickListener {
        fun OnGameClick (game: Game) {
        }
    }
}