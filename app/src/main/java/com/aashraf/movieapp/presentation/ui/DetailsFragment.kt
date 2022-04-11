package com.aashraf.movieapp.presentation.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import coil.load
import com.aashraf.movieapp.data.entities.MovieData
import com.aashraf.movieapp.databinding.FragmentDetailsBinding
import com.aashraf.movieapp.presentation.ui.HomeFragment.Companion.MOVIE_ITEM_KEY_BUNDLE
import com.aashraf.movieapp.utils.Constants

class DetailsFragment : Fragment() {
    private lateinit var binding: FragmentDetailsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val movieModel = arguments?.getParcelable<MovieData>(MOVIE_ITEM_KEY_BUNDLE)
        binding.apply {
            movieModel?.let {
                detailsMovieTitle.text = it.title
                detailsMovieReleaseDate.text = it.releaseDate
                tvVoteCount.text = it.voteCount.toString()
                detailsMovieDetailsLabel.text = it.overview.toString()
                detailsMoviePoster.load(Constants.IMAGE_BASE_URL + it.posterPath)
            }
        }
    }
}