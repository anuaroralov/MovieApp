package com.anuar.movieapp.presentation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.anuar.movieapp.data.network.ApiFactory.apiService
import com.anuar.movieapp.data.network.model.ListOfMovies
import com.anuar.movieapp.databinding.FragmentHomeBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


private var _binding: FragmentHomeBinding? = null
private val binding: FragmentHomeBinding
    get() = _binding ?: throw RuntimeException("FragmentHomeBinding is null")

private lateinit var adapter: MovieAdapter
class HomeFragment : Fragment() {


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

        adapter = MovieAdapter()
        binding.recyclerView.layoutManager = LinearLayoutManager(activity,LinearLayoutManager.HORIZONTAL,false)
        binding.recyclerView.adapter = adapter
        CoroutineScope(Dispatchers.IO).launch {
            val movieList: ListOfMovies =apiService.moviesList()
            activity?.runOnUiThread {
                adapter.submitList(movieList.results)
            }
            }

        }

}

