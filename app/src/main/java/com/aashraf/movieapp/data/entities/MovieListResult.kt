package com.aashraf.movieapp.data.entities

import com.google.gson.annotations.SerializedName
import com.aashraf.movieapp.data.entities.MovieData

class MovieListResult {
    @SerializedName("results")
    lateinit var movies: List<MovieData>
}