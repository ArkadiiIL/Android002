package com.arkadii.android002.presentation.adapters

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import com.arkadii.android002.R
import com.arkadii.android002.databinding.ItemPopularContentRvBinding
import com.arkadii.android002.domain.Content
import com.arkadii.android002.utils.ImageSizeUtil
import com.squareup.picasso.Picasso

class ContentViewHolder(private val binding: ItemPopularContentRvBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun setData(context: Context, content: Content) {
        content.apply {
            binding.textTitle.text = name
            val imageUrl = if (posterPath != null) {
                val imageSize = ImageSizeUtil.getImageSizeForScreen(context)
                "$IMG_URL$imageSize$posterPath"
            } else {
                val imageSize = ImageSizeUtil.getPlaceholderSizeForScreen(context)
                val imageText = context.getString(R.string.image_placeholder_text)
                "$PLACEHOLDER_URL$imageSize$PLACEHOLDER_TEXT_ATTR$imageText"
            }
            Picasso.get().load(imageUrl).into(binding.imagePoster)
        }

    }

    companion object {
        private const val IMG_URL = "https://image.tmdb.org/t/p/"
        private const val PLACEHOLDER_URL = "https://placehold.co/"
        private const val PLACEHOLDER_TEXT_ATTR = "jpg?text="
    }
}