package com.aashraf.movieapp.data.repository

import com.aashraf.movieapp.data.remote.MoviesAPI
import com.aashraf.movieapp.data.entities.MovieData
import com.aashraf.movieapp.domain.MoviesDataStore

class RemoteMoviesDataStore(private val moviesAPI: MoviesAPI) :
        MoviesDataStore {
    override suspend fun getMovies(): List<MovieData> {
        return moviesAPI.getPopularMovies().movies
    }
}


