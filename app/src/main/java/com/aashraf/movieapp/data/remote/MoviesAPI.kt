package com.aashraf.movieapp.data.remote

import com.aashraf.movieapp.data.entities.MovieListResult
import retrofit2.http.GET
import retrofit2.http.Query

interface MoviesAPI {
    @GET("movie/popular")
    suspend fun getPopularMovies( @Query("api_key") accessKey: String = "c50f5aa4e7c95a2a553d29b81aad6dd0"): MovieListResult
}