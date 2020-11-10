package com.example.bghub.Views.Fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.core.view.get
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.example.bghub.Commons.AppConstants.Join_Game_Room
import com.example.bghub.Commons.AppConstants.Show_Game_Details
import com.example.bghub.Models.ApiResponse.ApiResponse
import com.example.bghub.Models.GameRooms.GameRoom
import com.example.bghub.Models.GameRooms.JoinGameRoomPayload
import com.example.bghub.R
import com.example.bghub.Repositories.Data.DataContract
import com.example.bghub.Repositories.Http.HttpContract
import com.example.bghub.Utils.OnSingleClickListener
import com.example.bghub.Views.Main.MainActivity
import com.example.bghub.ui.CardStack.CustomCardStackLayoutManager
import com.example.bghub.ui.adapter.GameRoomAdapter
import com.yuyakaido.android.cardstackview.CardStackLayoutManager
import com.yuyakaido.android.cardstackview.CardStackListener
import com.yuyakaido.android.cardstackview.CardStackView
import com.yuyakaido.android.cardstackview.Direction
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_search_game.*

/**
 * Fragment used to look game offers.
 *
 * In this fragment, the swipe through the games people have offered in their vicinity.
 *
 * @author lcgal
 * @version 1.0
 * @since 1.0
 */
class SearchGameFragment : Fragment(), GameRoomAdapter.OnGameClickListener
{
    lateinit var mDataRepository: DataContract.Repository

    lateinit var mHttpRepository: HttpContract

    lateinit var adapter: GameRoomAdapter

    lateinit var mJoinButton: ImageButton

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_search_game, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = GameRoomAdapter( mDataRepository.gameRooms, this)
        stack_view.adapter = adapter

        var manager =  CardStackLayoutManager(activity)
        manager.setVisibleCount(2)
        stack_view.layoutManager =  manager

        setUpButtons()
    }

    private fun setUpButtons() {
        mJoinButton = join_button
        mJoinButton.setOnClickListener(object : OnSingleClickListener() {
            override fun onSingleClick(view: View) {

                var gameRoom = adapter.getCurrentRoom()
                joinGameRoomn(gameRoom)
                stack_view.swipe()
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
     * Method that deals with any clicks on the card.
     *
     * It's able to listen to the click because it implements the GameListAdapter.OnGameRowListener interface, and it passes it's own listener to the adapter @see onViewCreated.
     * The action argument is defined by what part of the card was clicked and what we should do.
     * @author lcgal
     * @param Models.Games.Game
     */
    override fun OnGameClick (gameRoom: GameRoom, action: String) {
        if (action == Show_Game_Details) {
            if (activity != null) {
                val manager: FragmentManager = activity!!.supportFragmentManager
                val transaction = manager.beginTransaction()
                val fragment: GameDetailsFragment = GameDetailsFragment.newInstance(gameRoom.game)
                transaction.replace(R.id.frameLayout, fragment)
                transaction.addToBackStack(null);
                transaction.commit()
            }
        }
    }

    fun joinGameRoomn (gameRoom: GameRoom) {
        //TODO create a class to manage api calls
        val payload  = JoinGameRoomPayload(gameRoom.id,mDataRepository.session.profile.userId)
        mHttpRepository.postJoinGameRoom(payload)
                .subscribeOn(Schedulers.newThread())
                .observeOn(Schedulers.newThread())
                .subscribeWith(object : DisposableObserver<ApiResponse<String>>() {
                    override fun onNext(response: ApiResponse<String>) {
                        if (response != null && response.result == true) {
                        }
                    }
                    override fun onError(e: Throwable) {
                        //TODO error treatment
                    }
                    override fun  onComplete() {
                    }
                })
    }

    companion object {
        @JvmStatic
        fun newInstance(dataRepository : DataContract.Repository, httpRepository: HttpContract): SearchGameFragment {
            val fragment = SearchGameFragment()
            fragment.setDataRepository(dataRepository)
            fragment.setHttpRepository(httpRepository)
            return fragment
        }
    }


}