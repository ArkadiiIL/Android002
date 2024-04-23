package com.arkadii.android002.presentation.adapters

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import com.arkadii.android002.R
import com.arkadii.android002.databinding.ItemPopularContentRvBinding
import com.arkadii.android002.domain.data.Content
import com.arkadii.android002.utils.ImageSizeUtil
import com.squareup.picasso.Picasso

class ContentViewHolder(private val binding: ItemPopularContentRvBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun setData(context: Context, content: Content, listener: ((Content) -> Unit)?) {
        content.apply {
            binding.textTitle.text = name
            val imageUrl = ImageSizeUtil.getImageUrl(context, posterPath)
            Picasso.get().load(imageUrl).into(binding.imagePoster)
        }
        if (listener != null) {
            binding.root.setOnClickListener {
                listener(content)
            }
        }
    }
}