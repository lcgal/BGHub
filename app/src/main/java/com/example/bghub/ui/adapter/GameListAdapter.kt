package com.example.bghub.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.example.bghub.Models.Games.Game
import com.example.bghub.ui.Holder.GameListHolder
import java.util.*
import kotlin.collections.ArrayList

class GameListAdapter(private val list: List<Game>)
    : RecyclerView.Adapter<GameListHolder>(), Filterable {

    lateinit var filteredList : List<Game>

    init {
        filteredList = list
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GameListHolder {
        val inflater = LayoutInflater.from(parent.context)
        return GameListHolder(inflater, parent)
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

}