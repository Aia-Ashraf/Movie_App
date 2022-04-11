package com.aashraf.movieapp.data.local.db

import androidx.room.*
import com.aashraf.movieapp.data.entities.MovieData

@Dao
interface MoviesDao {
    @Query("SELECT * FROM movies")
    suspend fun getFavorites(): List<MovieData>

    @Query("SELECT * FROM movies WHERE id=:movieId")
    suspend fun get(movieId: Int): MovieData?


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveMovie(movie: MovieData)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveAllMovies(movies: List<MovieData>)

    @Delete
    suspend fun removeMovie(movie: MovieData)

    @Query("DELETE FROM movies")
    suspend fun clear()
}
