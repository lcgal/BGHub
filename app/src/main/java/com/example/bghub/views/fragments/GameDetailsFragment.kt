package com.example.bghub.views.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.annotation.DrawableRes
import androidx.fragment.app.Fragment
import com.example.bghub.data.models.Games.Game
import com.example.bghub.R
import com.example.bghub.data.models.Games.GameWithChildren
import com.example.bghub.databinding.FragmentGameDetailsBinding
import com.squareup.picasso.Picasso
/**
 * Fragment to see details of a game.
 *
 * @author lcgal
 * @version 1.0
 * @since 1.0
 */
@Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
class GameDetailsFragment : Fragment()
{

    lateinit var mGame : GameWithChildren

    private var _binding: FragmentGameDetailsBinding? = null

    private val binding get() = _binding!!

    //Runs after onCreate(), and binds views and returns the view Hierarchy of the fragment
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGameDetailsBinding.inflate(inflater, container, false)

        return binding.root
    }

    //Runs after onCreateView, and can be used to set functionality to view elements
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.gameImage.loadThumbnailInList(mGame.getThumbnail())
        binding.gameName.text = mGame.getName()
        //TODO move this to the domain layer
        val lineSep = System.getProperty("line.separator")
        val description = mGame.getDescription().replace("<br/>",lineSep)
        binding.gameDescription.text = description
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun ImageView.loadThumbnailInList(imageUrl: String?, @DrawableRes errorResId: Int = R.drawable.thumbnail_image_empty) {
        Picasso.with(context)
                .load(imageUrl)
                .placeholder(errorResId)
                .error(errorResId)
                .fit()
                .centerCrop()
                .into(this)
    }


    //custom "constructor" to pass on depedencies
    companion object {
        @JvmStatic
        fun newInstance(game: GameWithChildren): GameDetailsFragment {
            val fragment = GameDetailsFragment()
            fragment.mGame = game
            return fragment
        }
    }

}