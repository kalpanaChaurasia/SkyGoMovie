package com.sky.skygomovie.api

import com.sky.skygomovie.data.Movies
import retrofit2.Response

interface ApiHelper {
    suspend fun getMovies():Response<Movies>
}