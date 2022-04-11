package com.aashraf.movieapp.presentation.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.aashraf.movieapp.R
import com.aashraf.movieapp.databinding.FragmentHomeBinding
import com.aashraf.movieapp.domain.usecases.GetPopularMoviesUseCase
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : Fragment() {
    private val viewModel: HomeViewModel by viewModels()
    private lateinit var binding: FragmentHomeBinding
    private lateinit var popularMoviesAdapter: PopularMoviesAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fetchPopularMovieList()
    }

    private fun fetchPopularMovieList() {
        lifecycleScope.launch {
            delay(500)
            viewModel.getPopularMovieList().collect {
                when (it) {
                    is State.DataState -> observeOnMoviesReceived(it)
                    is State.ErrorState -> onErrorRecived(it)
                    is State.LoadingState -> populateLoadingState()
                }
            }
        }
    }

    private fun populateLoadingState() {
        binding.popularMoviesProgress.visibility = VISIBLE
    }

    private fun onErrorRecived(it: State.ErrorState) {
        Toast.makeText(
            activity,
            it.exception.message,
            Toast.LENGTH_SHORT
        ).show()
    }

    private suspend fun observeOnMoviesReceived(it: State.DataState<GetPopularMoviesUseCase>) {
        binding.popularMoviesProgress.visibility = GONE
        popularMoviesAdapter = PopularMoviesAdapter { movie, _ ->
            val bundle = bundleOf(MOVIE_ITEM_KEY_BUNDLE to movie)
            findNavController().navigate(R.id.load_fragment, bundle)
        }
        binding.popularMoviesRecyclerview.adapter = popularMoviesAdapter
        popularMoviesAdapter.submitList(it.data.getMovies())
    }

    companion object {
        const val MOVIE_ITEM_KEY_BUNDLE = "MOVIE_ITEM_KEY_BUNDLE"
    }
}