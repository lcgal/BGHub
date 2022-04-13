package com.example.bghub.Views.Fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkManager
import androidx.work.WorkRequest
import com.example.bghub.BGHubApplication
import com.example.bghub.Background.UpdateGameRoomsWorker
import com.example.bghub.R
import com.example.bghub.Utils.OnSingleClickListener
import com.example.bghub.Views.Main.MainActivity
import com.example.bghub.databinding.FragmentMainMenuBinding
import com.example.bghub.databinding.FragmentOfferGameBinding

class MainMenuFragment : Fragment()
{
    lateinit var mOfferGameButton: Button

    lateinit var mTriggerUpdateButton: Button

    lateinit var mSearchGameButton: Button

    private var _binding: FragmentMainMenuBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        _binding = FragmentMainMenuBinding.inflate(inflater, container, false)
        val view = binding.root

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpButtons()
    }


    private fun setUpButtons() {
        //Button to open offer game fragment
        mOfferGameButton = binding.offerGameButton
        mOfferGameButton.setOnClickListener(object : OnSingleClickListener() {
            override fun onSingleClick(view: View) {
                val mainActivity: MainActivity? = activity as MainActivity?
                mainActivity?.openOfferGameFragment()
            }
        })

        //Button to open offer game fragment
        mTriggerUpdateButton = binding.searchGameButton
        mTriggerUpdateButton.setOnClickListener(object : OnSingleClickListener() {
            override fun onSingleClick(view: View) {
                val myWorkRequest: WorkRequest = OneTimeWorkRequest.from(UpdateGameRoomsWorker::class.java)
                WorkManager.getInstance(BGHubApplication.getAppContext()).enqueue(myWorkRequest)
            }
        })

        //Button to open search game fragment
        mSearchGameButton = binding.searchGameButton2
        mSearchGameButton.setOnClickListener(object : OnSingleClickListener() {
            override fun onSingleClick(view: View) {
                val mainActivity: MainActivity? = activity as MainActivity?
                mainActivity?.openSearchGameFragment()
            }
        })
    }

}