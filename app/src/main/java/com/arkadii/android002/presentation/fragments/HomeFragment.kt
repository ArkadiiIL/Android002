package com.arkadii.android002.presentation.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.arkadii.android002.databinding.FragmentHomeBinding
import com.arkadii.android002.presentation.activities.DetailContentActivity
import com.arkadii.android002.presentation.viewmodels.HomeViewModel
import com.arkadii.android002.presentation.adapters.PageContentAdapter

class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: HomeViewModel
    private lateinit var pageContentAdapter: PageContentAdapter


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this)[HomeViewModel::class.java]
        pageContentAdapter = PageContentAdapter(requireContext())
        pageContentAdapter.setListener {
            showDetailActivity(it.id.toLong(), it.isMovie)
        }

        binding.apply {
            rvPopularContentList.adapter = pageContentAdapter
        }

        viewModel.popularList.observe(viewLifecycleOwner) {
            pageContentAdapter.submitData(viewLifecycleOwner.lifecycle, it)
        }
        return binding.root
    }

    private fun showDetailActivity(contentId: Long, isMovie: Boolean) {
        startActivity(DetailContentActivity.getIntent(requireContext(), contentId, isMovie))
    }

    companion object {
        fun getInstance() = HomeFragment()
    }
}