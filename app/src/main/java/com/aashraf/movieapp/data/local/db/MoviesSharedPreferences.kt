package com.aashraf.movieapp.data.local.db

import android.content.Context
import android.content.SharedPreferences
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class MoviesSharedPreferences @Inject constructor(@ApplicationContext context: Context) {
    private val preferences: SharedPreferences =
        context.getSharedPreferences(APP_PREF, Context.MODE_PRIVATE)
    var cashedAppTime: Long
        get() = preferences.getLong(CASHED_APP_TIME, 0)
        set(value) = preferences.edit().putLong(CASHED_APP_TIME, value).apply()

    companion object {
        private const val APP_PREF = "APP_PREF"
        private const val CASHED_APP_TIME = "CASHED_APP_TIME"
    }
}