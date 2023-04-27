package com.sky.skygomovie.ui.movie

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sky.skygomovie.api.ApiHelper
import com.sky.skygomovie.api.ApiService
import com.sky.skygomovie.data.Movies
import com.sky.skygomovie.repository.MovieRepository
import com.sky.skygomovie.ui.model.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieViewModel @Inject constructor(private val movieRepository: MovieRepository) :
    ViewModel() {

    private val _resMovie = MutableStateFlow<Resource<Movies>>(Resource.Loading())
    val resMovie: StateFlow<Resource<Movies>> =  _resMovie

//    init {
//        getMovies()
//    }

    fun getMovies() = viewModelScope.launch {
        movieRepository.getMovies()
            .catch { exception ->
                _resMovie.value = Resource.Error(exception.message!!)
            }
            .collect {
                _resMovie.value = Resource.Success(it)
            }
    }


}