package com.example.bghub.Views.Fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.annotation.DrawableRes
import androidx.fragment.app.Fragment
import com.example.bghub.Models.Games.Game
import com.example.bghub.R
import com.example.bghub.databinding.FragmentGameDetailsBinding
import com.example.bghub.databinding.FragmentMainMenuBinding
import com.squareup.picasso.Picasso
/**
 * Fragment to see details of a game.
 *
 * @author lcgal
 * @version 1.0
 * @since 1.0
 */
class GameDetailsFragment : Fragment()
{

    lateinit var mGame : Game

    private var _binding: FragmentGameDetailsBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        _binding = FragmentGameDetailsBinding.inflate(inflater, container, false)
        val view = binding.root

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.gameImage.loadThumbnailInList(mGame.thumbnail)
        binding.gameName.setText(mGame.name)
        //TODO move this to an utils class
        val lineSep = System.getProperty("line.separator")
        val description = mGame.description.replace("<br/>",lineSep)
        binding.gameDescription.setText(description)
    }

    fun ImageView.loadThumbnailInList(imageUrl: String?, @DrawableRes errorResId: Int = R.drawable.thumbnail_image_empty) {
        Picasso.with(context)
                .load(imageUrl)
                .placeholder(errorResId)
                .error(errorResId)
                .fit()
                .centerCrop()
                .into(this)
    }


    companion object {
        @JvmStatic
        fun newInstance(game: Game): GameDetailsFragment {
            val fragment = GameDetailsFragment()
            fragment.mGame = game
            return fragment
        }
    }

}