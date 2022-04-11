package com.aashraf.movieapp.utils

import android.content.Context
import androidx.room.Room
import com.aashraf.movieapp.MovieApplication
import com.aashraf.movieapp.data.remote.MoviesAPI
import com.aashraf.movieapp.data.local.db.MoviesDao
import com.aashraf.movieapp.data.local.db.MoviesDatabase
import com.aashraf.movieapp.data.local.db.MoviesSharedPreferences
import com.aashraf.movieapp.data.repository.MoviesRepositoryImpl
import com.aashraf.movieapp.domain.MoviesRepository
import com.aashraf.movieapp.domain.usecases.GetPopularMoviesUseCase
import com.google.gson.GsonBuilder
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.Cache
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import timber.log.Timber
import java.io.File
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @Singleton
    @Provides
    fun provideApplication(@ApplicationContext app: Context): MovieApplication {
        return app as MovieApplication
    }

    @Provides
    @Singleton
    fun provideRetrofit(client: OkHttpClient): Retrofit {
        return Retrofit.Builder().baseUrl(Constants.BASE_URL).client(client)
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
            .build()
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(
        headerInterceptor: Interceptor,
        cache: Cache
    ): OkHttpClient {
        val loggingInterceptor = HttpLoggingInterceptor { message ->
            Timber.d(message)
        }.apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

        val okHttpClientBuilder = OkHttpClient().newBuilder()
        okHttpClientBuilder.addInterceptor(loggingInterceptor)
        okHttpClientBuilder.connectTimeout(CONNECTION_TIMEOUT.toLong(), TimeUnit.SECONDS)
        okHttpClientBuilder.readTimeout(READ_TIMEOUT.toLong(), TimeUnit.SECONDS)
        okHttpClientBuilder.writeTimeout(WRITE_TIMEOUT.toLong(), TimeUnit.SECONDS)
        okHttpClientBuilder.cache(cache)
        okHttpClientBuilder.addInterceptor(headerInterceptor)
        return okHttpClientBuilder.build()
    }

    @Provides
    @Singleton
    fun provideHeaderInterceptor(): Interceptor {
        return Interceptor {
            val requestBuilder = it.request().newBuilder()
            it.proceed(requestBuilder.build())
        }
    }


    @Provides
    @Singleton
    internal fun provideCache(context: Context): Cache {
        val httpCacheDirectory = File(context.cacheDir.absolutePath, "HttpCache")
        return Cache(httpCacheDirectory, CACHE_SIZE_BYTES)
    }


    @Provides
    @Singleton
    fun provideContext(application: MovieApplication): Context {
        return application.applicationContext
    }

    @Provides
    @Singleton
    fun provideApi(retrofit: Retrofit): MoviesAPI {
        return retrofit.create(MoviesAPI::class.java)
    }

    @Provides
    fun provideGetPopularMoviesUseCase(moviesRepository: MoviesRepositoryImpl): GetPopularMoviesUseCase {
        return GetPopularMoviesUseCase(moviesRepository)
    }

    companion object {
        private const val READ_TIMEOUT = 30
        private const val WRITE_TIMEOUT = 30
        private const val CONNECTION_TIMEOUT = 10
        private const val CACHE_SIZE_BYTES = 10 * 1024 * 1024L
    }
}

@Module
@InstallIn(ActivityComponent::class)
abstract class AnalyticsModule {
    @Binds
    abstract fun bindAnalyticsService(a: MoviesRepositoryImpl): MoviesRepository
}

@InstallIn(SingletonComponent::class)
@Module
class SharedPreferencesModule {
    @Provides
    fun provideSharedPreferences(@ApplicationContext context: Context): MoviesSharedPreferences {
        return MoviesSharedPreferences(context)
    }
}

@InstallIn(SingletonComponent::class)
@Module
class DatabaseModule {
    @Provides
    fun provideChannelDao(appDatabase: MoviesDatabase): MoviesDao {
        return appDatabase.getMoviesDao()
    }

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext appContext: Context): MoviesDatabase {
        return Room.databaseBuilder(
            appContext,
            MoviesDatabase::class.java,
            "RssReader"
        ).build()
    }
}