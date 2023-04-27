package com.sky.skygomovie.api

import com.sky.skygomovie.data.Movies
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ApiHelperImpl @Inject constructor(private val apiService: ApiService) : ApiHelper {
    override suspend fun getMovies(): Flow<Movies> =
        flow {
            emit(apiService.getMovies())
        }

}