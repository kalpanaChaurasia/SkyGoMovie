package com.sky.skygomovie.ui.movie

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.sky.skygomovie.data.MoviesItem
import com.sky.skygomovie.databinding.ItemMovieBinding
import java.util.*

class MovieAdapter() : RecyclerView.Adapter<MovieAdapter.ViewHolder>(),
    Filterable {
    private var mMoviesData: ArrayList<MoviesItem> = ArrayList()
    private var mDataFiltered: ArrayList<MoviesItem> = ArrayList()

    fun addMovieData(list: ArrayList<MoviesItem>) {
        mMoviesData = list
        mDataFiltered = mMoviesData
        notifyDataSetChanged()
    }

    inner class ViewHolder(val binding: ItemMovieBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(moviesItem: MoviesItem) {
            Glide.with(binding.ivMovie.context)
                .load(moviesItem.Poster)
                .into(binding.ivMovie)

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemMovieBinding.inflate(
                LayoutInflater.from(
                    parent.context
                )
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(mDataFiltered!![position])

    }

    override fun getItemCount(): Int {
        return mDataFiltered?.size ?: 0
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {

                val charString = constraint?.toString() ?: ""
                mDataFiltered = if (charString.isEmpty()) {
                    mMoviesData
                } else {
                    val filteredList = ArrayList<MoviesItem>()
                    mMoviesData.filter {
                        (it.Title.lowercase(Locale.getDefault())
                            .contains(charString.lowercase(Locale.getDefault())))
                                || (it.Genre.lowercase(Locale.getDefault())
                            .contains(charString.lowercase(Locale.getDefault())))

                    }.forEach { filteredList.add(it) }
                    filteredList
                }
                return FilterResults().apply { values = mDataFiltered }
            }

            override fun publishResults(charSequence: CharSequence, filterResults: FilterResults) {
                mDataFiltered = if (filterResults.values == null)
                    ArrayList()
                else
                    filterResults.values as ArrayList<MoviesItem>
                notifyDataSetChanged()
            }
        }
    }
}