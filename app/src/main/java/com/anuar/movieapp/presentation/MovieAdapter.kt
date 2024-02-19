package com.anuar.movieapp.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.anuar.movieapp.data.network.MovieDto
import com.anuar.movieapp.databinding.ItemMovieBinding
import com.squareup.picasso.Picasso

class MovieAdapter() : ListAdapter<MovieDto, MovieAdapter.MovieViewHolder>(CoinInfoDiffCallback) {

    class MovieViewHolder(
        val binding: ItemMovieBinding
    ) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val binding = ItemMovieBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return MovieViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movie = getItem(position)
        with(holder.binding) {
            with(movie) {
                textView.text =title
                textView2.text=vote_average.toString()

                Picasso.get().load(BASE_URL+poster_path).into(imageView)
            }
        }
    }

    object CoinInfoDiffCallback : DiffUtil.ItemCallback<MovieDto>() {

        override fun areItemsTheSame(oldItem: MovieDto, newItem: MovieDto): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: MovieDto, newItem: MovieDto): Boolean {
            return oldItem == newItem
        }
    }
    companion object{
        private val BASE_URL = "https://image.tmdb.org/t/p/w500/"
    }
}

