package com.example.bghub.Views.Fragments.OfferGame


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

class OfferGameFragment : Fragment() , GameListAdapter.OnGameRowListener {

    lateinit var mDataRepository: DataContract.Repository

    lateinit var mHttpRepository: HttpContract

    lateinit var adapter: GameListAdapter

    lateinit var disposableObserver: DisposableObserver<ApiResponse<String>>

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
        if (location == null){
            mDataRepository.updateLocation(this.context)
            return
        }

        var gameid = game.id
        var latitude = location.latitude
        var longitude = location.longitude
        var sessionid = mDataRepository.session.profile.userId
        var gameOffer = GameOffer(gameid,
                        latitude,
                        longitude,
                        sessionid)
                //Sending game offer
                //TODO send this to the background
                mHttpRepository.postGameOffer(gameOffer)
                        .subscribeOn(Schedulers.newThread())
                        .observeOn(Schedulers.newThread())
                        .subscribeWith(object : DisposableObserver<ApiResponse<String>>() {
                            override fun onNext(response: ApiResponse<String>?) {
                                if (response != null && response.result == true) {
                                    var gameRoom = GameRoom(response.returnData,latitude,longitude,sessionid,gameid,game)
                                    mDataRepository.insertGameRoom(gameRoom)
                                    getActivity()?.supportFragmentManager?.popBackStack();
                                }
                            }
                            override fun  onError(e: Throwable?) {
                                //TODO error treatment
                            }

                            override fun  onComplete() {
                            }})
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