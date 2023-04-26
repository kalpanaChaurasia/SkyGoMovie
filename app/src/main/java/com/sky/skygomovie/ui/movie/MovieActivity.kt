package com.sky.skygomovie.ui.movie

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.GridLayoutManager
import com.sky.skygomovie.R
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
        movieAdapter = MovieAdapter()

        //setup action bar

        //add search

        //add observer
        observeMovieData()

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.search_menu, menu)
        val searchItem: MenuItem = menu.findItem(R.id.actionSearch)
        val searchView: SearchView = searchItem.actionView as SearchView

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener,
            android.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                movieAdapter.filter.filter(query)
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                movieAdapter.filter.filter(newText)
                return false
            }
        })
        return true
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
        data?.let {
            movieAdapter.addMovieData(it)
            binding.rvMovies.apply {
                layoutManager = GridLayoutManager(
                    applicationContext,
                    resources.getInteger(R.integer.grid_column)
                )
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