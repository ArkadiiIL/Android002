package com.arkadii.android002.presentation.fragments

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import com.arkadii.android002.databinding.FragmentProfileBinding
import com.arkadii.android002.presentation.activities.DetailContentActivity
import com.arkadii.android002.presentation.activities.WebViewActivity
import com.arkadii.android002.presentation.adapters.PageContentAdapter
import com.arkadii.android002.presentation.viewmodels.ProfileViewModel
import com.squareup.picasso.Picasso
import kotlinx.coroutines.launch

class ProfileFragment : Fragment() {
    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: ProfileViewModel
    private lateinit var webViewResultLauncher: ActivityResultLauncher<Intent>
    private lateinit var favoriteMovieAdapter: PageContentAdapter
    private lateinit var favoriteTvAdapter: PageContentAdapter
    private var requestToken: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this)[ProfileViewModel::class.java]

        webViewResultLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                if (result.resultCode == Activity.RESULT_OK && requestToken != null) {
                    viewModel.login(requestToken!!)
                    hideAuthorizationField()
                } else {
                    hideProgressCircular()
                    showAuthorizationField()
                }
            }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this)[ProfileViewModel::class.java]
        favoriteMovieAdapter = PageContentAdapter(requireContext())
        favoriteTvAdapter = PageContentAdapter(requireContext())
        if (viewModel.isSessionActive()) {
            viewModel.getUser()
        } else {
            showAuthorizationField()
        }
        binding.apply {
            rvFavoriteMovieList.adapter = favoriteMovieAdapter
            rvFavoriteTvList.adapter = favoriteTvAdapter
            favoriteMovieAdapter.setListener { showDetailActivity(it.id.toLong(), it.isMovie) }
            favoriteTvAdapter.setListener { showDetailActivity(it.id.toLong(), it.isMovie) }
            buttonLogin.setOnClickListener {
                viewModel.getRequestToken()
                showProgressCircular()
                hideAuthorizationField()
            }
            buttonLogout.setOnClickListener {
                viewModel.logOut()
                hideUserDetails()
                showAuthorizationField()
            }
        }
        viewModel.apply {
            updateRequestToken.observe(viewLifecycleOwner) {
                requestToken = it
                startWebViewActivity(it)
            }
            updateUser.observe(viewLifecycleOwner) { user ->
                hideAuthorizationField()
                hideProgressCircular()
                binding.apply {
                    tvId.text = user.id.toString()
                    tvName.text = user.name
                    tvUserName.text = user.userName
                    if (user.avatarPath != null) {
                        Picasso.get().load(getAvatarUrl(user.avatarPath))
                            .into(ivAvatar)
                    } else {
                        Picasso.get().load(getAvatarPlaceholderUrl(user.userName)).into(ivAvatar)
                    }
                }
                showUserDetails()
            }
            authError.observe(viewLifecycleOwner) {
                hideUserDetails()
                hideProgressCircular()
                showAuthorizationField()
            }
        }
        lifecycleScope.launch {
            favoriteMovieAdapter.loadStateFlow.collect { loadState ->
                if (loadState.source.refresh is LoadState.NotLoading) {
                    if (favoriteMovieAdapter.itemCount == 0) {
                        binding.favoriteMovieListTitle.visibility = View.GONE
                    } else {
                        binding.favoriteMovieListTitle.visibility = View.VISIBLE
                    }
                }
            }
        }

        lifecycleScope.launch {
            favoriteTvAdapter.loadStateFlow.collect { loadState ->
                if (loadState.source.refresh is LoadState.NotLoading) {
                    if (favoriteTvAdapter.itemCount == 0) {
                        binding.favoriteTvListTitle.visibility = View.GONE
                    } else {
                        binding.favoriteTvListTitle.visibility = View.VISIBLE
                    }
                }
            }
        }
        return binding.root
    }

    private fun hideProgressCircular() {
        binding.progressCircular.visibility = View.GONE
    }

    private fun showProgressCircular() {
        binding.progressCircular.visibility = View.VISIBLE
    }

    private fun showAuthorizationField() {
        binding.authorizationField.visibility = View.VISIBLE
    }

    private fun showUserDetails() {
        binding.userDetails.visibility = View.VISIBLE
        loadFavoriteLists()
    }

    private fun hideAuthorizationField() {
        binding.authorizationField.visibility = View.GONE
    }

    private fun hideUserDetails() {
        binding.userDetails.visibility = View.GONE
    }

    private fun getAvatarPlaceholderUrl(username: String) = "$AVATAR_PLACEHOLDER_URL$username"

    private fun getAvatarUrl(avatarPath: String) = "$IMAGE_BASE_URL$IMAGE_SIZE$avatarPath"

    private fun startWebViewActivity(requestToken: String) {
        val url = getAuthorizationUrl(requestToken)
        webViewResultLauncher.launch(WebViewActivity.getIntent(requireContext(), url, requestToken))
    }

    private fun getAuthorizationUrl(requestToken: String) = "$AUTHORIZATION_URL$requestToken"

    private fun showDetailActivity(contentId: Long, isMovie: Boolean) {
        startActivity(DetailContentActivity.getIntent(requireContext(), contentId, isMovie))
    }

    private fun loadFavoriteLists() {
        viewModel.getFavoriteMoviesList().observe(viewLifecycleOwner) {
            favoriteMovieAdapter.submitData(viewLifecycleOwner.lifecycle, it)
        }
        viewModel.getFavoriteTvList().observe(viewLifecycleOwner) {
            favoriteTvAdapter.submitData(viewLifecycleOwner.lifecycle, it)
        }
    }

    companion object {
        fun getInstance() = ProfileFragment()
        const val AVATAR_PLACEHOLDER_URL = "https://avatar.iran.liara.run/username?username="
        const val AUTHORIZATION_URL = "https://www.themoviedb.org/authenticate/"
        private const val IMAGE_BASE_URL = "https://image.tmdb.org/t/p/"
        private const val IMAGE_SIZE = "w185"
    }
}