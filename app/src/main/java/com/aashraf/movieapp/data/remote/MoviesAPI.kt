package com.aashraf.movieapp.data.remote

import com.aashraf.movieapp.BuildConfig
import com.aashraf.movieapp.data.entities.MovieListResult
import retrofit2.http.GET
import retrofit2.http.Query

interface MoviesAPI {
    @GET("movie/popular")
    suspend fun getPopularMovies( @Query("api_key") accessKey: String = BuildConfig.API_AUTH_KEY): MovieListResult
}