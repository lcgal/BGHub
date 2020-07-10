package com.example.bghub.ui.Holder

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.DrawableRes
import androidx.recyclerview.widget.RecyclerView
import com.example.bghub.Models.Games.Game
import com.example.bghub.R
import com.squareup.picasso.Picasso


class GameListHolder(inflater: LayoutInflater, parent: ViewGroup) :
        RecyclerView.ViewHolder(inflater.inflate(R.layout.game_row, parent, false)) {
    private var mThumbnailView: ImageView? = null
    private var mNameView: TextView? = null



    init {
        mThumbnailView = itemView.findViewById(R.id.thumbnail)
        mNameView = itemView.findViewById(R.id.name)
    }

    fun bind(game: Game) {
        mThumbnailView?.loadThumbnailInList(game.thumbnail)
        mNameView?.text = game.name
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
