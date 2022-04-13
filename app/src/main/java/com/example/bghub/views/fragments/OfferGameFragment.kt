package com.example.bghub.views.fragments


import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bghub.data.models.apiResponse.ApiResponse
import com.example.bghub.data.models.GameRooms.GameOffer
import com.example.bghub.data.models.GameRooms.GameRoom
import com.example.bghub.data.models.Games.Game
import com.example.bghub.data.services.data.DbContract
import com.example.bghub.data.services.Http.HttpContract
import com.example.bghub.databinding.FragmentOfferGameBinding
import com.example.bghub.views.lists.adapter.GameListAdapter
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers

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

    lateinit var mDataRepository: DbContract

    private lateinit var mHttpRepository: HttpContract

    lateinit var adapter: GameListAdapter

    private var _binding: FragmentOfferGameBinding? = null

    private val binding get() = _binding!!

    //Runs after onCreate(), and binds views and returns the view Hierarchy of the fragment
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentOfferGameBinding.inflate(inflater, container, false)

        return binding.root
    }

    /**
     * OnViewCreated sets up the adapter (GameListAdapter), a LinearLayoutManager, to which it passes a custom listener @see OnGameRowClick, and a listener for changes on the search bar that filters the games list.
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = GameListAdapter( mDataRepository.gamesList, this)
        binding.gamesRecyclerView.adapter = adapter
        binding.gamesRecyclerView.layoutManager =  LinearLayoutManager(activity)

        binding.gameNameSearch.setOnQueryTextListener(object: SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                adapter.filter.filter(newText)
                return false
            }
        })

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun setDataRepository(dataRepository : DbContract) {
        mDataRepository = dataRepository

    }

    fun setHttpRepository(httpRepository : HttpContract) {
        mHttpRepository = httpRepository

    }

    /**
     * Method that deals with the click on the Holder row.
     * It's able to listen to the click because it implements the GameListAdapter.OnGameRowListener interface, and it passes it's own listener to the adapter @see onViewCreated.
     */
    @SuppressLint("CheckResult")
    override fun OnGameRowClick (game: Game) {
        val location = mDataRepository.userLocation

        if (location == null) {
            //TODO error message
            return
        }

        val gameid = game.id
        val latitude = location.latitude
        val longitude = location.longitude
        val userId = location.userId
        val gameOffer = GameOffer(gameid,
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
                                if (response.result == true) {
                                    val gameRoom = GameRoom(response.returnData,latitude,longitude,userId,gameid,game)
                                    mDataRepository.insertGameRoom(gameRoom)
                                    activity?.supportFragmentManager?.popBackStack()
                                }
                                else {
                                    //TODO
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
     */
    companion object {
        @JvmStatic
        fun newInstance(dataRepository : DbContract, httpRepository: HttpContract): OfferGameFragment {
            val fragment = OfferGameFragment()
            fragment.setDataRepository(dataRepository)
            fragment.setHttpRepository(httpRepository)
            return fragment
        }
    }
}