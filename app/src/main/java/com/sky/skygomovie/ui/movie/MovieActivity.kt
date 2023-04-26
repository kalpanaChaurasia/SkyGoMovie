package com.sky.skygomovie.ui.movie

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.sky.skygomovie.data.Movies
import com.sky.skygomovie.databinding.ActivityMovieBinding
import com.sky.skygomovie.ui.movie.MovieViewModel.Resource
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MovieActivity : AppCompatActivity() {

    private val movieViewModel: MovieViewModel by viewModels()
    private lateinit var movieAdapter: MovieAdapter
    private lateinit var binding: ActivityMovieBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMovieBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //setup action bar
        //add search
        //add observer
        observeMovieData()

    }

    private fun observeMovieData() {
        movieViewModel.resMovie.observe(this) {
            when (it) {
                is Resource.Loading -> handleLoadingState(it.isLoading)
                is Resource.Success -> handleSuccessState(it.data)
                is Resource.Error -> handleErrorState(it.error)
            }
        }
    }

    private fun handleErrorState(error: String) {
        println("error  $error")
    }

    private fun handleSuccessState(data: Movies?) {
        println("data?.size  ${data?.size}")
        data?.let {
            movieAdapter = MovieAdapter(it)
            binding.rvMovies.apply {
                layoutManager = GridLayoutManager(applicationContext, 2)
                adapter = movieAdapter
            }
        }
    }

    private fun handleLoadingState(loading: Boolean) {
        if (loading) {
            //display progress
        } else {
            //hide it
        }


    }

}