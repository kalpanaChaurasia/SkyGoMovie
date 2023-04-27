package com.sky.skygomovie.ui.movie

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.gson.GsonBuilder
import com.google.gson.JsonParser
import com.sky.skygomovie.api.ApiHelper
import com.sky.skygomovie.repository.MovieRepository
import com.sky.skygomovie.utils.JsonUtils
import junit.framework.Assert
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import okhttp3.OkHttpClient
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.jupiter.api.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class MovieDetailApiTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    private val gson = GsonBuilder()
        .setLenient()
        .create()

    private val mockWebServer = MockWebServer()

    private lateinit var mockedResponse: String
    private lateinit var movieRepository: MovieRepository

    @Before
    fun init() {
        mockWebServer.start(8000)
        val client = OkHttpClient.Builder()
            .connectTimeout(1, TimeUnit.SECONDS)
            .readTimeout(1, TimeUnit.SECONDS)
            .writeTimeout(1, TimeUnit.SECONDS)
            .build()

        val api = Retrofit.Builder()
            .baseUrl(mockWebServer.url("/"))
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiHelper::class.java)

        movieRepository = MovieRepository(api)
    }

    @Test
    fun testApiSuccess() {
        mockedResponse = JsonUtils.readStringFromFile("assets/movie_data.json")
        mockWebServer.enqueue(
            MockResponse()
                .setResponseCode(200)
                .setBody(mockedResponse)
        )
        runBlocking {
            movieRepository.getMovies().collect {
                val json = gson.toJson(it)
                val resultResponse = JsonParser.parseString(json)
                val expectedresponse = JsonParser.parseString(mockedResponse)
                Assert.assertNotNull(it)
                Assert.assertTrue(resultResponse.equals(expectedresponse))
            }
        }


    }


    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }

}