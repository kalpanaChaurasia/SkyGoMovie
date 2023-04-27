package com.sky.skygomovie.repository

import com.sky.skygomovie.api.ApiHelper
import javax.inject.Inject

class MovieRepository @Inject constructor(private val apiHelper: ApiHelper) {
    suspend fun getMovies() = apiHelper.getMovies()
}