package com.anuar.movieapp.presentation.Fragments

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.anuar.movieapp.databinding.FragmentFavouritesBinding
import com.anuar.movieapp.domain.Movie
import com.anuar.movieapp.presentation.MovieAdapter
import com.anuar.movieapp.presentation.MyApplication
import com.anuar.movieapp.presentation.MyViewModel
import com.anuar.movieapp.presentation.MyViewModelFactory
import javax.inject.Inject

class FavouritesFragment : Fragment() {

    private var _binding: FragmentFavouritesBinding? = null
    private val binding: FragmentFavouritesBinding
        get() = _binding ?: throw RuntimeException("FragmentFavouritesBinding is null")

    private lateinit var adapter: MovieAdapter

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
        _binding = FragmentFavouritesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this, viewModelFactory)[MyViewModel::class.java]

        adapter = MovieAdapter(){ movie ->
            launchDetailFragment(movie)
        }

        binding.favouritesRecyclerView.layoutManager = GridLayoutManager(activity,4)
        binding.favouritesRecyclerView.adapter = adapter

//        viewModel.favouriteMoviesList.observe(viewLifecycleOwner) {
//            adapter.submitList(it)
//        }

    }

    private fun launchDetailFragment(movie: Movie) {
        findNavController().navigate(FavouritesFragmentDirections.actionFavouritesFragmentToDetailFragment(movie))
    }
}