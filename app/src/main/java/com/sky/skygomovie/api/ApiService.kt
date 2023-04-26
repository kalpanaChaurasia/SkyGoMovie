package com.sky.skygomovie.api

import com.sky.skygomovie.data.Movies
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {

    @GET("759fdfa82d6f33522e11")
    suspend fun getMovies():Response<Movies>
}