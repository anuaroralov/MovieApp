package com.anuar.movieapp.presentation.Fragments

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
import com.anuar.movieapp.presentation.MovieAdapter
import com.anuar.movieapp.presentation.MyViewModel


class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding: FragmentHomeBinding
        get() = _binding ?: throw RuntimeException("FragmentHomeBinding is null")

    private lateinit var adapter: MovieAdapter

    private val viewModel by lazy{
        ViewModelProvider(this)[MyViewModel::class.java]
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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

        adapter = MovieAdapter(){launchDetailFragment(it)}
        binding.recyclerView.layoutManager = LinearLayoutManager(activity,LinearLayoutManager.HORIZONTAL,false)
        binding.recyclerView.adapter = adapter

        viewModel.movieList.observe(viewLifecycleOwner){
            adapter.submitList(it)
        }


        }

    private fun launchDetailFragment(movie: Movie) {
        findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToDetailFragment(movie))
    }

}

