package com.example.bghub.Views.Fragments.OfferGame


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.bghub.Models.GameRooms.GameOffer
import com.example.bghub.Models.Games.Game
import com.example.bghub.R
import com.example.bghub.Repositories.Data.DataContract
import com.example.bghub.Repositories.Data.DataRepository
import com.example.bghub.Repositories.Http.HttpContract
import com.example.bghub.Repositories.Http.HttpRepository
import com.example.bghub.ui.adapter.GameListAdapter
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.fragment_offer_game.*
import javax.inject.Inject

class OfferGameFragment : Fragment() , GameListAdapter.OnGameRowListener {

    lateinit var mDataRepository: DataContract.Repository

    lateinit var mHttpRepository: HttpContract

    lateinit var adapter: GameListAdapter

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_offer_game, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = GameListAdapter( mDataRepository.gamesList, this)
        games_recycler_view.adapter = adapter
        games_recycler_view.layoutManager =  LinearLayoutManager(activity)

        game_name_search.setOnQueryTextListener(object: SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                adapter.filter.filter(newText)
                return false
            }
        })

    }

    fun setDataRepository(dataRepository : DataContract.Repository) {
        mDataRepository = dataRepository;

    }

    fun setHttpRepository(httpRepository : HttpContract) {
        mHttpRepository = httpRepository;

    }

    override fun OnGameRowClick (game: Game) {
        var location = mDataRepository.getLocation()
        var gameOffer = GameOffer(game.id,
                location.latitude,
                location.longitude,
                mDataRepository.session.profile.userId)
        mHttpRepository.postGameOffer(gameOffer)
        //TODO subscribe to get the result.


    }

    companion object {
        @JvmStatic
        fun newInstance(dataRepository : DataContract.Repository, httpRepository: HttpContract): OfferGameFragment {
            val fragment = OfferGameFragment()
            fragment.setDataRepository(dataRepository)
            fragment.setHttpRepository(httpRepository)
            return fragment
        }
    }

    //inner class recycler
}