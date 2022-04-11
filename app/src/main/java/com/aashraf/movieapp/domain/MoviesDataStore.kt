package com.aashraf.movieapp.domain

import com.aashraf.movieapp.data.entities.MovieData


interface MoviesDataStore {
    suspend fun getMovies(): List<MovieData>

}
