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
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_game_details.*

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

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_game_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        game_image.loadThumbnailInList(mGame.thumbnail)
        game_name.setText(mGame.name)
        //TODO move this to an utils class
        val lineSep = System.getProperty("line.separator")
        val description = mGame.description.replace("<br/>",lineSep)
        game_description.setText(description)
    }

    fun ImageView.loadThumbnailInList(imageUrl: String?, @DrawableRes errorResId: Int = R.drawable.thumbnail_image_empty) {
        Picasso.get()
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