package com.arkadii.android002.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.arkadii.android002.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: HomeViewModel
    private lateinit var popularContentListAdapter: PopularContentAdapter


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this)[HomeViewModel::class.java]
        popularContentListAdapter = PopularContentAdapter(requireContext())
        val popularContentListLayoutManager = GridLayoutManager(requireContext(), 3)

        binding.apply {
            rvPopularContentList.layoutManager = popularContentListLayoutManager
            rvPopularContentList.adapter = popularContentListAdapter
        }

        viewModel.popularList.observe(viewLifecycleOwner) {
            popularContentListAdapter.submitData(viewLifecycleOwner.lifecycle, it)
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    companion object {
        fun getInstance() = HomeFragment()
    }
}