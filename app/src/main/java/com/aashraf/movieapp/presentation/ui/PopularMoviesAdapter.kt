package com.aashraf.movieapp.presentation.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.aashraf.movieapp.utils.Constants
import com.aashraf.movieapp.data.entities.MovieData
import com.aashraf.movieapp.databinding.PopularMoviesItemRowBinding

class PopularMoviesAdapter(
    private val onMovieSelected:
        (MovieData, View) -> Unit
) :
    ListAdapter<MovieData, PopularMoviesAdapter.MovieCellViewHolder>(PopularMoviesDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieCellViewHolder {
        return MovieCellViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: MovieCellViewHolder, position: Int) {
        val movieItem = getItem(position)
        holder.bind(movieItem, onMovieSelected)
    }

    class MovieCellViewHolder private constructor(private val binding: PopularMoviesItemRowBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(movie: MovieData, listener: (MovieData, View) -> Unit) {
            binding.movieItemTitle.text = movie.originalTitle
            movie.posterPath?.let {
                binding.movieItemImage.load(Constants.IMAGE_BASE_URL + it)
            }
            binding.root.setOnClickListener {
                listener(movie, itemView)
            }
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): MovieCellViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = PopularMoviesItemRowBinding.inflate(layoutInflater, parent, false)
                return MovieCellViewHolder(binding)
            }
        }
    }

    class PopularMoviesDiffCallback : DiffUtil.ItemCallback<MovieData>() {
        override fun areItemsTheSame(oldItem: MovieData, newItem: MovieData): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: MovieData, newItem: MovieData): Boolean {
            return oldItem == newItem
        }
    }
}
