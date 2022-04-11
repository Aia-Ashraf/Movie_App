package com.aashraf.movieapp.presentation.ui

import androidx.lifecycle.ViewModel
import com.aashraf.movieapp.domain.usecases.GetPopularMoviesUseCase
import com.aashraf.movieapp.utils.Utils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.channelFlow
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val getPopularMoviesUseCase: GetPopularMoviesUseCase) :
    ViewModel() {

    fun getPopularMovieList(): Flow<State<GetPopularMoviesUseCase>> = channelFlow {
        send(State.LoadingState)
        try {
            send(State.DataState(getPopularMoviesUseCase))
        } catch (e: Exception) {
            e.printStackTrace()
            send(Utils.resolveError(e))
        }
    }
}