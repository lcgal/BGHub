package com.example.bghub.views.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.bghub.R
import com.example.bghub.repositories.data.DataContract

class ProfileFragment : Fragment() {

    private lateinit var mDataRepository: DataContract.Repository

    //Runs after onCreate(), and binds views and returns the view Hierarchy of the fragment
    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    fun setDataRepository(dataRepository : DataContract.Repository) {
        mDataRepository = dataRepository
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