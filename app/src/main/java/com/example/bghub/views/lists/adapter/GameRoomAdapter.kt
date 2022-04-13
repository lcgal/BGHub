package com.example.bghub.views.lists.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.DrawableRes
import androidx.recyclerview.widget.RecyclerView
import com.example.bghub.commons.AppConstants.Show_Game_Details
import com.example.bghub.data.models.GameRooms.GameRoom
import com.example.bghub.R
import com.squareup.picasso.Picasso

class GameRoomAdapter(private val gameRoomList: List<GameRoom>, onGameClickListener: OnGameClickListener)
    : RecyclerView.Adapter<GameRoomAdapter.GameRoomHolder>() {

    var mOnGameClickListener: OnGameClickListener

    lateinit var mTopGameRoom: GameRoom
    lateinit var mBotGameRoom: GameRoom

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
        //A little weird logic here. What we want to do is access the gameroom currently being displayed on the top holder.
        if(!this::mTopGameRoom.isInitialized){
            mTopGameRoom = gameRoom
        } else if (!this::mBotGameRoom.isInitialized) {
            mBotGameRoom = gameRoom
        } else {
            mTopGameRoom = mBotGameRoom
            mBotGameRoom = gameRoom
        }
        holder.bind(gameRoom)
    }

    fun getCurrentRoom () :  GameRoom {
        return mTopGameRoom
    }

    inner class GameRoomHolder(inflater: LayoutInflater, parent: ViewGroup, onGameClickListener: OnGameClickListener) :
            RecyclerView.ViewHolder(inflater.inflate(R.layout.card_view_game, parent, false))  {
        //TODO WHY THE FUCK CAN'T CARDVIEW WRAP ALL OF THE CONTENT I'M FUCKING TIRED OF THIS SHIT.
        private var mImageView: ImageView? = null
        private var mNameView: TextView? = null
        private var mDistanceView: TextView? = null
        private var mOnGameClickListener : OnGameClickListener

        init {
            mImageView = itemView.findViewById(R.id.game_image)
            mNameView = itemView.findViewById(R.id.game_name)
            mDistanceView = itemView.findViewById(R.id.distance)
            mOnGameClickListener = onGameClickListener
        }

        fun bind(gameRoom: GameRoom) {
            var game = gameRoom.game
            mImageView?.loadThumbnailInList(game.thumbnail)
            mNameView?.text = game.name
            mDistanceView?.text = gameRoom.distance.toString()

            itemView.setOnClickListener{
                mOnGameClickListener.OnGameClick(gameRoomList.get(adapterPosition), Show_Game_Details)

            }

        }

        fun getCurrentItem() : GameRoom {
            return gameRoomList.get(adapterPosition)
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
        fun OnGameClick (gameRoom: GameRoom, action: String) {
        }
    }
}