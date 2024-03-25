package com.anuar.movieapp.presentation.Fragments

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.anuar.movieapp.R
import com.anuar.movieapp.databinding.FragmentDetailBinding
import com.anuar.movieapp.domain.Movie
import com.anuar.movieapp.presentation.MyApplication
import com.anuar.movieapp.presentation.MyViewModel
import com.anuar.movieapp.presentation.MyViewModelFactory
import com.squareup.picasso.Picasso
import javax.inject.Inject

class DetailFragment : Fragment() {

    private val args by navArgs<DetailFragmentArgs>()

    private var _binding: FragmentDetailBinding? = null
    private val binding: FragmentDetailBinding
        get() = _binding ?: throw RuntimeException("FragmentDetailBinding is null")

    @Inject
    lateinit var viewModelFactory: MyViewModelFactory

    private lateinit var viewModel: MyViewModel

    private val component by lazy {
        (requireActivity().application as MyApplication).component
    }

    override fun onAttach(context: Context) {
        component.inject(this)
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this, viewModelFactory)[MyViewModel::class.java]
        val movie = args.movie

        setupUI(movie)
        setupFavouriteButton(movie)
    }

    private fun setupUI(movie: Movie) {
        with(binding) {
            Picasso.get().load(movie.posterPath).into(moviePoster)
            movieOverview.text = movie.overview
            movieTitle.text = movie.title
            movieVoteAverage.text = movie.voteAverage.toString()
        }
    }

    private fun setupFavouriteButton(movie: Movie) {
        val isLike: Boolean = movie.favourite
        val favButton = binding.fav

        val favDrawableRes = if (isLike) R.drawable.fav else R.drawable.non_fav
        favButton.setBackgroundResource(favDrawableRes)

        favButton.setOnClickListener {
            viewModel.updateFavouriteStatus(movie.id)
            val newFavDrawableRes = if (isLike) R.drawable.non_fav else R.drawable.fav
            favButton.setBackgroundResource(newFavDrawableRes)
        }
    }



}