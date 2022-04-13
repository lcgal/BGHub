package com.example.bghub.views.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.bghub.R
import com.example.bghub.data.services.data.DbContract

class ProfileFragment : Fragment() {

    private lateinit var mDataRepository: DbContract

    //Runs after onCreate(), and binds views and returns the view Hierarchy of the fragment
    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    fun setDataRepository(dataRepository : DbContract) {
        mDataRepository = dataRepository
    }

    companion object {
        @JvmStatic
        fun newInstance(dataRepository : DbContract): ProfileFragment {
            val fragment = ProfileFragment()
            fragment.setDataRepository(dataRepository)
            return fragment
        }
    }


}