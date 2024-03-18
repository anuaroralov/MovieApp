package com.anuar.movieapp.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.anuar.movieapp.databinding.ItemMovieCategoryBinding
import com.anuar.movieapp.domain.Movie
import com.anuar.movieapp.domain.MovieCategory

class MovieCategoryAdapter(private val onClickListener: (Movie) -> Unit) :
    ListAdapter<MovieCategory, MovieCategoryAdapter.CategoryViewHolder>(MovieCategoryDiffCallback) {

    class CategoryViewHolder(binding: ItemMovieCategoryBinding, onClickListener: (Movie) -> Unit) :
        RecyclerView.ViewHolder(binding.root) {
        val categoryNameTextView = binding.categoryNameTextView
        val moviesRecyclerView = binding.moviesRecyclerView
        val movieAdapter = MovieAdapter(onClickListener).also { adapter ->
            moviesRecyclerView.layoutManager = LinearLayoutManager(
                moviesRecyclerView.context,
                LinearLayoutManager.HORIZONTAL,
                false
            )
            moviesRecyclerView.adapter = adapter
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val binding =
            ItemMovieCategoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CategoryViewHolder(binding, onClickListener)
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        val category = getItem(position)
        with(holder) {
            categoryNameTextView.text = category.categoryName
            movieAdapter.submitList(category.movies)
        }
    }

    companion object {
        private val MovieCategoryDiffCallback = object : DiffUtil.ItemCallback<MovieCategory>() {
            override fun areItemsTheSame(oldItem: MovieCategory, newItem: MovieCategory): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: MovieCategory, newItem: MovieCategory): Boolean =
                oldItem == newItem
        }
    }
}
