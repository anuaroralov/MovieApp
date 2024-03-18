package com.anuar.movieapp.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.anuar.movieapp.databinding.ItemMovieBinding
import com.anuar.movieapp.domain.Movie
import com.squareup.picasso.Picasso

class MovieAdapter(private val onClickListener:(Movie)->Unit) : ListAdapter<Movie, MovieAdapter.MovieViewHolder>(MovieDiffCallback) {

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

                textView2.text=voteAverage.toString()

                Picasso.get().load(posterPath).into(imageView)

                root.setOnClickListener{
                    onClickListener(movie)
                }
            }
        }
    }

    object MovieDiffCallback : DiffUtil.ItemCallback<Movie>() {

        override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem == newItem
        }
    }
}

