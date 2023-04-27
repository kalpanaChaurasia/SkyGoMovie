package com.sky.skygomovie.api

import com.google.gson.Gson
import com.sky.skygomovie.data.Movies
import com.sky.skygomovie.utils.JsonUtils
import javax.inject.Inject

class FakeApiService @Inject constructor() : ApiService {

    var failUserApi: Boolean = false
    var wrongResponse: Boolean = false

    override suspend fun getMovies(): Movies {
        if (failUserApi) throw Exception("Api failed")
        return Gson().fromJson(
            JsonUtils.readStringFromFile(MOVIE_JSON),
            Movies::class.java
        )
    }

    companion object {
        private const val MOVIE_JSON = "assets/movie_data.json"
    }
}