package com.arkadii.android002.presentation

import android.content.Context
import android.opengl.Visibility
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.SearchView
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.Fragment
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.paging.PagingData
import com.arkadii.android002.databinding.FragmentSearchBinding
import com.arkadii.android002.domain.Content
import com.arkadii.android002.presentation.adapters.PageContentAdapter
import kotlinx.coroutines.launch

class SearchFragment : Fragment() {
    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: SearchViewModel
    private lateinit var moviesListAdapter: PageContentAdapter
    private lateinit var tvListAdapter: PageContentAdapter
    private val searchMediator =
        MediatorLiveData<Pair<PagingData<Content>?, PagingData<Content>?>?>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this)[SearchViewModel::class.java]
        moviesListAdapter = PageContentAdapter(requireContext())
        tvListAdapter = PageContentAdapter(requireContext())
        binding.apply {
            rvSearchMovieList.adapter = moviesListAdapter
            rvSearchTvList.adapter = tvListAdapter
            searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String): Boolean {
                    hideKeyboard()
                    binding.progressCircular.visibility = View.VISIBLE

                    searchMediator.value = null
                    val moviesLiveData = viewModel.searchMoviesByTitle(query)
                    val tvLiveData = viewModel.searchTvByTitle(query)
                    searchMediator.addSource(moviesLiveData) { movies ->
                        searchMediator.value = Pair(movies, tvLiveData.value)
                    }
                    searchMediator.addSource(tvLiveData) { tv ->
                        searchMediator.value = Pair(moviesLiveData.value, tv)
                    }

                    return true
                }

                override fun onQueryTextChange(newText: String): Boolean {

                    return true
                }
            })

            searchMediator.observe(viewLifecycleOwner) { content ->
                content?.apply {
                    if (first != null && second != null) {
                        binding.apply {
                            progressCircular.visibility = View.GONE


                            moviesListAdapter.submitData(viewLifecycleOwner.lifecycle, first!!)
                            tvListAdapter.submitData(viewLifecycleOwner.lifecycle, second!!)
                        }
                    }
                }
            }
        }

        lifecycleScope.launch {
            moviesListAdapter.loadStateFlow.collect { loadState ->
                if (loadState.source.refresh is LoadState.NotLoading) {
                    if (moviesListAdapter.itemCount == 0) {
                        binding.llMoviesListBlock.visibility = View.GONE
                    } else {
                        binding.llMoviesListBlock.visibility = View.VISIBLE
                    }
                }
            }
        }

        lifecycleScope.launch {
            tvListAdapter.loadStateFlow.collect { loadState ->
                if (loadState.source.refresh is LoadState.NotLoading) {
                    if (tvListAdapter.itemCount == 0) {
                        binding.llTvListBlock.visibility = View.GONE
                    } else {
                        binding.llTvListBlock.visibility = View.VISIBLE
                    }
                }
            }
        }
        return binding.root
    }

    private fun hideKeyboard() {
        val imm = requireActivity()
            .getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        val view = requireActivity().currentFocus
        view?.let {
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }

    companion object {
        fun getInstance() = SearchFragment()
    }
}