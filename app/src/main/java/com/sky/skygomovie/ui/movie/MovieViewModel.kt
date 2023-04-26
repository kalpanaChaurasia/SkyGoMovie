package com.sky.skygomovie.ui.movie

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sky.skygomovie.data.Movies
import com.sky.skygomovie.repository.MovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieViewModel @Inject constructor(private val movieRepository: MovieRepository) :
    ViewModel() {

    private val _resMovie = MutableLiveData<Resource<Movies>>()

    val resMovie: LiveData<Resource<Movies>>
        get() = _resMovie

    init {
        getMovies()
    }

     fun getMovies() = viewModelScope.launch {
        try {
            _resMovie.value = Resource.Loading(true)
            movieRepository.getMovie().let {
                _resMovie.value = Resource.Loading(false)
                if (it.isSuccessful) {
                    _resMovie.value = Resource.Success(it.body())
                } else {
                    _resMovie.value = Resource.Error(it.errorBody().toString())
                }
            }
        } catch (e: Exception) {
            _resMovie.value = Resource.Loading(false)
            _resMovie.value = Resource.Error(e.message.toString())
        }
    }

    sealed class Resource<T> {
        data class Loading<T>(val isLoading: Boolean) : Resource<T>()
        data class Success<T>(val data: Movies?) : Resource<T>()
        data class Error<T>(val error: String) : Resource<T>()
    }
}