package com.arkadii.android002.presentation.fragments

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkRequest
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import com.arkadii.android002.databinding.FragmentHomeBinding
import com.arkadii.android002.presentation.activities.DetailContentActivity
import com.arkadii.android002.presentation.viewmodels.HomeViewModel
import com.arkadii.android002.presentation.adapters.PageContentAdapter
import com.arkadii.android002.presentation.network.NetworkCallback
import kotlinx.coroutines.launch

class HomeFragment : Fragment(), NetworkCallback.OnNetworkChangeListener {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: HomeViewModel
    private lateinit var pageContentAdapter: PageContentAdapter
    private lateinit var connectivityManager: ConnectivityManager
    private lateinit var networkCallback: NetworkCallback
    private var readyToConnect = false


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this)[HomeViewModel::class.java]
        setAdapter()
        connectivityManager =
            requireContext().getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        networkCallback = NetworkCallback(this)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            connectivityManager.registerDefaultNetworkCallback(networkCallback)
        } else {
            val builder = NetworkRequest.Builder()
            connectivityManager.registerNetworkCallback(builder.build(), networkCallback)
        }

        viewModel.popularList.observe(viewLifecycleOwner) {
            pageContentAdapter.submitData(viewLifecycleOwner.lifecycle, it)

        }
        return binding.root
    }

    private fun showDetailActivity(contentId: Long, isMovie: Boolean) {
        startActivity(DetailContentActivity.getIntent(requireContext(), contentId, isMovie))
    }

    private fun setAdapter() {
        binding.progressCircular.visibility = View.VISIBLE
        binding.llPopularContent.visibility = View.GONE
        pageContentAdapter = PageContentAdapter(requireContext())
        pageContentAdapter.setListener {
            showDetailActivity(it.id.toLong(), it.isMovie)
        }
        binding.rvPopularContentList.adapter = pageContentAdapter
        lifecycleScope.launch {
            pageContentAdapter.loadStateFlow.collect { loadState ->
                if (loadState.source.refresh is LoadState.NotLoading) {
                    if (pageContentAdapter.itemCount != 0) {
                        binding.progressCircular.visibility = View.GONE
                        binding.llPopularContent.visibility = View.VISIBLE
                    } else {
                        binding.progressCircular.visibility = View.GONE
                        binding.llPopularContent.visibility = View.VISIBLE
                    }
                }
            }
        }
        readyToConnect = true
    }

    companion object {
        fun getInstance() = HomeFragment()
    }

    override fun onNetworkChanged(isConnected: Boolean) {
        if (isConnected) pageContentAdapter.refresh()
    }
}