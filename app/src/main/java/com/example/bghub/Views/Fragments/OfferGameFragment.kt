package com.example.bghub.Views.Fragments


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bghub.Models.ApiResponse.ApiResponse
import com.example.bghub.Models.GameRooms.GameOffer
import com.example.bghub.Models.GameRooms.GameRoom
import com.example.bghub.Models.Games.Game
import com.example.bghub.R
import com.example.bghub.Repositories.Data.DataContract
import com.example.bghub.Repositories.Http.HttpContract
import com.example.bghub.ui.adapter.GameListAdapter
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_offer_game.*

/**
 * Fragment used to offer a game.
 *
 * In this fragment, the user should be able to easily search for a game it own, and offer it so that other users around his area can see it and get in touch.
 * Decided to use kotlin, and so far have decided to keep all it's functionalities contained in this single class, with only more application scoped dependencies (no viewmodel for example)
 *
 * @author lcgal
 * @version 1.0
 * @since 1.0
 */
class OfferGameFragment : Fragment() , GameListAdapter.OnGameRowListener {

    lateinit var mDataRepository: DataContract.Repository

    lateinit var mHttpRepository: HttpContract

    lateinit var adapter: GameListAdapter

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_offer_game, container, false)
    }

    /**
     * OnViewCreated sets up the adapter (GameListAdapter), a LinearLayoutManager, to which it passes a custom listener @see OnGameRowClick, and a listener for changes on the search bar that filters the games list.
     *
     * @author lcgal
     * @version 1.0
     * @since 1.0
     */
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

    /**
     * Method that deals with the click on the Holder row.
     *
     * It's able to listen to the click because it implements the GameListAdapter.OnGameRowListener interface, and it passes it's own listener to the adapter @see onViewCreated.
     *
     * @author lcgal
     * @param Models.Games.Game
     */
    override fun OnGameRowClick (game: Game) {
        var location = mDataRepository.userLocation

        if (location == null) {
            //TODO error message
            return
        }

        var gameid = game.id
        var latitude = location.latitude
        var longitude = location.longitude
        var userId = location.userId
        var gameOffer = GameOffer(gameid,
                        latitude,
                        longitude,
                        userId)
                //Sending game offer
                //TODO create a class to manage api calls
                mHttpRepository.postGameOffer(gameOffer)
                        .subscribeOn(Schedulers.newThread())
                        .observeOn(Schedulers.newThread())
                        .subscribeWith(object : DisposableObserver<ApiResponse<String>>() {
                            override fun onNext(response: ApiResponse<String>) {
                                if (response != null && response.result == true) {
                                    var gameRoom = GameRoom(response.returnData,latitude,longitude,userId,gameid,game)
                                    mDataRepository.insertGameRoom(gameRoom)
                                    getActivity()?.supportFragmentManager?.popBackStack();
                                }
                            }

                            override fun onError(e: Throwable) {
                                //TODO error treatment
                            }
                            override fun  onComplete() {
                            }
                        })
    }

    /**
     * Custom buider in order to get the necessary repositories from the activity.
     *
     * @author lcgal
     * @param  DataContract.Repository, HttpRepository
     * @version 1.0
     * @since 1.0
     */
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