package com.arkadii.android002.presentation.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.arkadii.android002.R
import com.arkadii.android002.databinding.ActivityDetailContentBinding
import com.arkadii.android002.presentation.viewmodels.DetailContentViewModel
import com.arkadii.android002.utils.ImageSizeUtil
import com.squareup.picasso.Picasso

class DetailContentActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailContentBinding
    private lateinit var viewModel: DetailContentViewModel
    private var contentId: Long = 0
    private var isMovie = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailContentBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel = ViewModelProvider(this)[DetailContentViewModel::class.java]
        contentId = intent.getLongExtra(CONTENT_ID_KEY, 0)
        isMovie = intent.getBooleanExtra(IS_MOVIE_KEY, false)

        viewModel.getDetailContent(contentId, isMovie)

        viewModel.updateContentDetail.observe(this) { contentDetail ->
            binding.apply {
                val url = ImageSizeUtil.getImageDetailUrl(
                    this@DetailContentActivity,
                    contentDetail.posterPath
                )
                Picasso.get().load(url).into(ivPoster)
                tvTitle.text = contentDetail.name
                tvYear.text = contentDetail.releaseYear
                if (contentDetail.isMovie) tvDuration.text = getRuntimeText(contentDetail.runtime)
                else tvDuration.text = getNumberEpisodeText(contentDetail.numberOfEpisodes)
                tvOverview.text = contentDetail.overview
                if (contentDetail.genres.isEmpty()) tvGenreTitle.visibility = View.GONE
                tvGenre.text = contentDetail.genres
                if (contentDetail.cast.isEmpty()) tvCastTitle.visibility = View.GONE
                tvActors.text = contentDetail.cast
            }
        }
    }

    private fun getRuntimeText(text: String?): String {
        return if (text != null) {
            val postFix = getString(R.string.movie_runtime_postfix)
            "$text $postFix"
        } else ""

    }

    private fun getNumberEpisodeText(text: String?): String {
        return if (text != null) {
            val preFix = getString(R.string.tv_number_of_episode_prefix)
            "$preFix $text"
        } else ""
    }

    companion object {
        fun getIntent(context: Context, contentId: Long, isMovie: Boolean): Intent {
            return Intent(context, DetailContentActivity::class.java).apply {
                putExtra(CONTENT_ID_KEY, contentId)
                putExtra(IS_MOVIE_KEY, isMovie)
            }
        }

        private const val CONTENT_ID_KEY = "content_id_key"
        private const val IS_MOVIE_KEY = "is_movie_key"
    }
}