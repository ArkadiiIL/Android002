package com.arkadii.android002.presentation.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import com.arkadii.android002.databinding.ItemPopularContentRvBinding
import com.arkadii.android002.domain.Content

class PageContentAdapter(private val context: Context) :
    PagingDataAdapter<Content, ContentViewHolder>(ContentDiffCallBack()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContentViewHolder {
        val binding = ItemPopularContentRvBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ContentViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ContentViewHolder, position: Int) {
        if (getItem(position) != null) {
            holder.setData(context, getItem(position)!!)
        }
    }
}