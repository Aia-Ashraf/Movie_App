package com.aashraf.movieapp.data.repository

import com.aashraf.movieapp.data.entities.MovieData
import com.aashraf.movieapp.data.local.db.MoviesDao
import com.aashraf.movieapp.data.local.db.MoviesSharedPreferences
import com.aashraf.movieapp.data.remote.MoviesAPI
import com.aashraf.movieapp.domain.MoviesDataStore
import com.aashraf.movieapp.domain.MoviesRepository
import javax.inject.Inject

class MoviesRepositoryImpl @Inject constructor(
    private val moviesAPI: MoviesAPI,
    private val sharedPreferences: MoviesSharedPreferences,
    private val dao: MoviesDao
) : MoviesRepository {

    private lateinit var remoteDataStore: MoviesDataStore
    private var currentTime = System.currentTimeMillis()

    override suspend fun getMovies(): List<MovieData> {
        return if (isCashValid()) {
            dao.getFavorites()
        } else {
            remoteDataStore = RemoteMoviesDataStore(moviesAPI)
            val result = remoteDataStore.getMovies()
            dao.saveAllMovies(result)
            sharedPreferences.cashedAppTime = currentTime
            result
        }
    }

    private fun isCashValid(): Boolean {
        return currentTime - sharedPreferences.cashedAppTime > FOUR_HOURS_IN_MILLIS
    }

    companion object {
        private const val FOUR_HOURS_IN_MILLIS: Long = 14400000
    }
}