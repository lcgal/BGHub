package com.example.bghub.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.DrawableRes
import androidx.recyclerview.widget.RecyclerView
import com.example.bghub.Models.Games.Game
import com.example.bghub.R
import com.example.bghub.ui.adapter.GameListAdapter.*
import com.squareup.picasso.Picasso
import java.util.*
import kotlin.collections.ArrayList

class GameListAdapter(private val list: List<Game>, onGameRowListener: OnGameRowListener)
    : RecyclerView.Adapter<GameListHolder>(), Filterable {

    lateinit var filteredList : List<Game>
    lateinit var mOnGameRowListener: OnGameRowListener

    init {
        filteredList = list
        mOnGameRowListener = onGameRowListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GameListHolder {
        val inflater = LayoutInflater.from(parent.context)
        return GameListHolder(inflater, parent, mOnGameRowListener)
}

    override fun onBindViewHolder(holder: GameListHolder, position: Int) {
        val game: Game = filteredList[position]
        holder.bind(game)
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val charSearch = constraint.toString()
                if (charSearch.isEmpty()) {
                    filteredList = list
                } else {
                    val resultList = ArrayList<Game>()
                    for (row in list) {
                        if (row.toLowerCase(Locale.ROOT).contains(charSearch.toLowerCase(Locale.ROOT))) {
                            resultList.add(row)
                        }
                    }
                    filteredList = resultList
                }
                val filterResults = FilterResults()
                filterResults.values = filteredList
                return filterResults

            }

            @Suppress("UNCHECKED_CAST")
            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                filteredList = results?.values as List<Game>
                notifyDataSetChanged()
            }
        }
    }

    fun filterByType(constraint: CharSequence?, filterType: String) {

    }

    override fun getItemCount(): Int = filteredList.size

    inner class GameListHolder(inflater: LayoutInflater, parent: ViewGroup, onGameRowListener: OnGameRowListener) :
            RecyclerView.ViewHolder(inflater.inflate(R.layout.game_row, parent, false)), View.OnClickListener {
        private var mThumbnailView: ImageView? = null
        private var mNameView: TextView? = null
        private lateinit var mHolderOnGameRowListener: OnGameRowListener

        init {
            mThumbnailView = itemView.findViewById(R.id.thumbnail)
            mNameView = itemView.findViewById(R.id.name)
            mHolderOnGameRowListener = onGameRowListener

        }

        fun bind(game: Game) {
            mThumbnailView?.loadThumbnailInList(game.thumbnail)
            mNameView?.text = game.name

            itemView.setOnClickListener{
                mHolderOnGameRowListener.OnGameRowClick(filteredList.get(adapterPosition))
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



        override fun onClick(p0: View?) {
        }
    }

    interface OnGameRowListener {
        fun OnGameRowClick (game: Game) {
        }
    }

}