package com.example.bghub.Views.Fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.example.bghub.Models.Games.Game
import com.example.bghub.R
import com.example.bghub.Repositories.Data.DataContract
import com.example.bghub.Repositories.Http.HttpContract
import com.example.bghub.ui.adapter.GameRoomAdapter
import com.yuyakaido.android.cardstackview.CardStackLayoutManager
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

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_search_game, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = GameRoomAdapter( mDataRepository.gameRooms, this)
        stack_view.adapter = adapter
        stack_view.layoutManager =  CardStackLayoutManager(activity)

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
    override fun OnGameClick (game: Game) {
        if (activity != null) {
            val manager: FragmentManager = activity!!.supportFragmentManager
            val transaction = manager.beginTransaction()
            val fragment: GameDetailsFragment = GameDetailsFragment.newInstance(game)
            transaction.replace(R.id.frameLayout, fragment)
            transaction.addToBackStack(null);
            transaction.commit()
        }
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