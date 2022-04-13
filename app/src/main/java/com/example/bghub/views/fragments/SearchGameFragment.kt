package com.example.bghub.views.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.example.bghub.commons.AppConstants.Show_Game_Details
import com.example.bghub.data.models.GameRooms.GameRoom
import com.example.bghub.R
import com.example.bghub.data.services.data.DbContract
import com.example.bghub.data.services.Http.HttpContract
import com.example.bghub.commons.utils.OnSingleClickListener
import com.example.bghub.databinding.FragmentSearchGameBinding
import com.example.bghub.views.lists.adapter.GameRoomAdapter
import com.yuyakaido.android.cardstackview.CardStackLayoutManager


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
    private lateinit var mDataRepository: DbContract.Repository

    private lateinit var mHttpRepository: HttpContract

    lateinit var adapter: GameRoomAdapter

    private lateinit var mJoinButton: ImageButton

    private var _binding: FragmentSearchGameBinding? = null

    private val binding get() = _binding!!

    //Can be used to set up non View dependent variables
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        adapter = GameRoomAdapter( mDataRepository.gameRooms, this)
    }

    //Runs after onCreate(), and binds views and returns the view Hierarchy of the fragment
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentSearchGameBinding.inflate(inflater, container, false)

        return binding.root
    }

    //Runs after onCreateView, and can be used to set functionality to view elements
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.stackView.adapter = adapter

        val manager =  CardStackLayoutManager(activity)
        manager.setVisibleCount(2)
        binding.stackView.layoutManager =  manager

        setUpButtons()
    }

    private fun setUpButtons() {
        mJoinButton = binding.joinButton
        mJoinButton.setOnClickListener(object : OnSingleClickListener() {
            override fun onSingleClick(view: View) {

                val gameRoom = adapter.getCurrentRoom()
                joinGameRoom(gameRoom)
                binding.stackView.swipe()
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun setDataRepository(dataRepository : DbContract.Repository) {
        mDataRepository = dataRepository

    }

    fun setHttpRepository(httpRepository : HttpContract) {
        mHttpRepository = httpRepository
    }

    /**
     * Method that deals with any clicks on the card.
     *
     * It's able to listen to the click because it implements the GameListAdapter.OnGameRowListener interface, and it passes it's own listener to the adapter @see onViewCreated.
     * The action argument is defined by what part of the card was clicked and what we should do.
     * @author lcgal
     */
    override fun OnGameClick (gameRoom: GameRoom, action: String) {
        if (action == Show_Game_Details) {
            if (activity != null) {
                val manager: FragmentManager = requireActivity().supportFragmentManager
                val transaction = manager.beginTransaction()
                val fragment: GameDetailsFragment = GameDetailsFragment.newInstance(gameRoom.game)
                transaction.replace(R.id.frameLayout, fragment)
                transaction.addToBackStack(null)
                transaction.commit()
            }
        }
    }

    fun joinGameRoom (gameRoom: GameRoom) {
        //TODO create a class to manage api calls
//        val payload  = JoinGameRoomPayload(gameRoom.id,mDataRepository.session.profile.userId)
//        mHttpRepository.postJoinGameRoom(payload)
//                .subscribeOn(Schedulers.newThread())
//                .observeOn(Schedulers.newThread())
//                .subscribeWith(object : DisposableObserver<ApiResponse<String>>() {
//                    override fun onNext(response: ApiResponse<String>) {
//                        if (response != null && response.result == true) {
//                        }
//                    }
//                    override fun onError(e: Throwable) {
//                        //TODO error treatment
//                    }
//                    override fun  onComplete() {
//                    }
//                })
    }

    companion object {
        @JvmStatic
        fun newInstance(dataRepository : DbContract.Repository, httpRepository: HttpContract): SearchGameFragment {
            val fragment = SearchGameFragment()
            fragment.setDataRepository(dataRepository)
            fragment.setHttpRepository(httpRepository)
            return fragment
        }
    }


}