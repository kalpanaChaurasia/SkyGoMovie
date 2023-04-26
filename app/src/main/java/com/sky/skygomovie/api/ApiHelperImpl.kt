package com.sky.skygomovie.api

import com.sky.skygomovie.data.Movies
import retrofit2.Response
import javax.inject.Inject

class ApiHelperImpl @Inject constructor(private val apiService: ApiService): ApiHelper {
    override suspend fun getMovies(): Response<Movies> =  apiService.getMovies()

}