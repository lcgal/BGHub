package com.example.bghub.Views.Fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bghub.R
import com.example.bghub.Repositories.Data.DataContract

class ProfileFragment : Fragment() {

    lateinit var mDataRepository: DataContract.Repository

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    fun setDataRepository(dataRepository : DataContract.Repository) {
        mDataRepository = dataRepository;
    }

    companion object {
        @JvmStatic
        fun newInstance(dataRepository : DataContract.Repository): ProfileFragment {
            val fragment = ProfileFragment()
            fragment.setDataRepository(dataRepository)
            return fragment
        }
    }


}