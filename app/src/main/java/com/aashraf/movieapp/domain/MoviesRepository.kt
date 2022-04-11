package com.aashraf.movieapp.domain

import com.aashraf.movieapp.data.entities.MovieData

interface MoviesRepository{
    suspend fun getMovies(): List<MovieData>
}