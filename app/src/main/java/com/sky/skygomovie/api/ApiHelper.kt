package com.sky.skygomovie.api

import com.sky.skygomovie.data.Movies
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

interface ApiHelper {
    suspend fun getMovies(): Flow<Movies>
}