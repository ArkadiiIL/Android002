package com.arkadii.android002.presentation.adapters

import androidx.recyclerview.widget.DiffUtil
import com.arkadii.android002.domain.data.Content

class ContentDiffCallBack : DiffUtil.ItemCallback<Content>() {
    override fun areItemsTheSame(oldItem: Content, newItem: Content) = oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: Content, newItem: Content) = oldItem == newItem
}