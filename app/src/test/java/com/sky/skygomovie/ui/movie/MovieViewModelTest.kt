package com.sky.skygomovie.ui.movie

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.gson.Gson
import com.sky.skygomovie.api.ApiHelper
import com.sky.skygomovie.api.FakeApiService
import com.sky.skygomovie.data.Movies
import com.sky.skygomovie.repository.MovieRepository
import com.sky.skygomovie.ui.model.Resource
import com.sky.skygomovie.utils.JsonUtils
import junit.framework.Assert
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.jupiter.api.Assertions
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner


@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class MovieViewModelTest {
    @get:Rule
    val rule = InstantTaskExecutorRule()

    val testDispatcher = TestCoroutineDispatcher()

    private lateinit var movieViewModel: MovieViewModel

    lateinit var movies: Flow<Movies>

    @Mock
    private lateinit var apiHelper: ApiHelper

    @Mock
    private lateinit var movieRepository: MovieRepository


    companion object {
        fun getMovies(): Flow<Movies> =
            flow {
                emit(
                    Gson().fromJson(
                        JsonUtils.readStringFromFile("assets/movie_data.json"),
                        Movies::class.java
                    )
                )

            }
    }

    @Before
    fun setupViewModel() {
        Dispatchers.setMain(testDispatcher)
        MockitoAnnotations.initMocks(this)
        viewModelSetUp()
    }

    private fun viewModelSetUp() {
        movies = getMovies()
        movieViewModel = MovieViewModel(movieRepository)
    }


    @Test
    fun testMovieDataSuccess() {
        runBlockingTest {
//            Mockito.doReturn(movies).`when`(movieRepository.getMovies())
            Mockito.`when`(movieRepository.getMovies()).thenReturn(movies)
            movieViewModel.getMovies()
//            Shadows.shadowOf(Looper.getMainLooper()).idle()
            val value = movieViewModel.resMovie.value
            Assert.assertTrue(value is Resource.Success)
            Assert.assertNotNull(value.data)
            Assertions.assertEquals(10, value.data?.size)
        }
    }

    @Test
    fun testMovieApiFailure() = runBlockingTest {
//        Mockito.doThrow(RuntimeException("Api Failed")).`when`(movieRepository.getMovies())
        Mockito.`when`(movieRepository.getMovies()).thenThrow(RuntimeException("Api Failed"))
        movieViewModel.getMovies()
//        Shadows.shadowOf(Looper.getMainLooper()).idle()
        val value = movieViewModel.resMovie.value
        Assert.assertTrue(value is Resource.Error)
        Assert.assertNull(value.data)
    }


    @After
    fun tearDown() {
        Dispatchers.resetMain()
        testDispatcher.cleanupTestCoroutines()
    }
}