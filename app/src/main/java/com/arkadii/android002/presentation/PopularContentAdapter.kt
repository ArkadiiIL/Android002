package com.arkadii.android002.presentation

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.RecyclerView
import com.arkadii.android002.databinding.ItemPopularContentRvBinding
import com.arkadii.android002.domain.Content
import com.arkadii.android002.utils.ImageSizeUtil
import com.squareup.picasso.Picasso

class PopularContentAdapter(private val context: Context) :
    PagingDataAdapter<Content, PopularContentAdapter.PopularContentViewHolder>(ContentDiffCallBack()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PopularContentViewHolder {
        val binding = ItemPopularContentRvBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return PopularContentViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PopularContentViewHolder, position: Int) {
        if (getItem(position) != null) {
            getItem(position)!!.apply {
                holder.binding.textTitle.text = name
                val imageSize = ImageSizeUtil.getImageSizeForScreen(context)
                val imageUrl = "$IMG_URL$imageSize$posterPath"
                Picasso.get().load(imageUrl).into(holder.binding.imagePoster)
            }
        }
    }

    class PopularContentViewHolder(val binding: ItemPopularContentRvBinding) :
        RecyclerView.ViewHolder(binding.root)

    companion object {
        private const val IMG_URL = "https://image.tmdb.org/t/p/"
    }
}