package com.example.bghub.views.fragments

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
import com.example.bghub.background.UpdateGameRoomsWorker
import com.example.bghub.commons.utils.OnSingleClickListener
import com.example.bghub.views.activities.main.MainActivity
import com.example.bghub.databinding.FragmentMainMenuBinding

/**
 * Fragment with selection of the app main functionalities
 *
 * @author lcgal
 * @version 1.0
 * @since 1.0
 */
class MainMenuFragment : Fragment()
{
    private lateinit var mOfferGameButton: Button

    private lateinit var mTriggerUpdateButton: Button

    private lateinit var mSearchGameButton: Button

    private var _binding: FragmentMainMenuBinding? = null

    private val binding get() = _binding!!

    //Runs after onCreate(), and binds views and returns the view Hierarchy of the fragment
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentMainMenuBinding.inflate(inflater, container, false)

        return binding.root
    }

    //Runs after onCreateView, and can be used to set functionality to view elements
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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}