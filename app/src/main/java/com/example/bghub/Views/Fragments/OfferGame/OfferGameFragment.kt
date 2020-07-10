package com.example.bghub.Views.Fragments.OfferGame


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.bghub.R
import com.example.bghub.Repositories.Data.DataContract
import com.example.bghub.ui.adapter.GameListAdapter
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.fragment_offer_game.*
import javax.inject.Inject

class OfferGameFragment : Fragment() {

    lateinit var mDataRepository: DataContract.Repository

    lateinit var adapter: GameListAdapter

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_offer_game, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = GameListAdapter( mDataRepository.gamesList)
        games_recycler_view.adapter = adapter
        games_recycler_view.layoutManager =  LinearLayoutManager(activity)

    }

    fun setDataRepository(dataRepository : DataContract.Repository) {
        mDataRepository = dataRepository;

    }

    companion object {
        @JvmStatic
        fun newInstance(dataRepository : DataContract.Repository): OfferGameFragment {
            val fragment = OfferGameFragment()
            fragment.setDataRepository(dataRepository)
            return fragment
        }
    }

    //inner class recycler
}