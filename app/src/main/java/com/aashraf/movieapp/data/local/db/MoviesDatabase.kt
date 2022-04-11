package com.aashraf.movieapp.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.aashraf.movieapp.data.entities.MovieData

@Database(entities = [MovieData::class], version = 1,exportSchema = false)
abstract class MoviesDatabase  : RoomDatabase() {
    abstract fun getMoviesDao(): MoviesDao
}
