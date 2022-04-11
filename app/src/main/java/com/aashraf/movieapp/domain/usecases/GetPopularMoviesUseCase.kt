package com.aashraf.movieapp.domain.usecases

import com.aashraf.movieapp.data.entities.MovieData
import com.aashraf.movieapp.data.repository.MoviesRepositoryImpl

class GetPopularMoviesUseCase constructor(private val moviesRepository: MoviesRepositoryImpl) {
    suspend fun getMovies(): List<MovieData> {
        return moviesRepository.getMovies()
    }
}
