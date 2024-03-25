package com.anuar.movieapp.presentation.Fragments

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.anuar.movieapp.databinding.FragmentHomeBinding
import com.anuar.movieapp.domain.Movie
import com.anuar.movieapp.presentation.MovieCategoryAdapter
import com.anuar.movieapp.presentation.MyApplication
import com.anuar.movieapp.presentation.MyViewModel
import com.anuar.movieapp.presentation.MyViewModelFactory
import javax.inject.Inject


class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding: FragmentHomeBinding
        get() = _binding ?: throw RuntimeException("FragmentHomeBinding is null")

    private lateinit var adapter: MovieCategoryAdapter

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
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this, viewModelFactory)[MyViewModel::class.java]

        adapter = MovieCategoryAdapter() { movie ->
            launchDetailFragment(movie)
        }

        binding.swipeRefreshLayout.setOnRefreshListener {
            viewModel.refreshData()
        }

        viewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            binding.swipeRefreshLayout.isRefreshing = isLoading
        }

        binding.recyclerView.layoutManager = LinearLayoutManager(activity)
        binding.recyclerView.adapter = adapter

        viewModel.movieCategoriesList.observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }

    }


    private fun launchDetailFragment(movie: Movie) {
        findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToDetailFragment(movie))
    }

}

