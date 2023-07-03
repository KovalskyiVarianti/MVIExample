package com.example.mviexample.presentation.list.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.example.mviexample.presentation.list.EntityListItem

class EntityListAdapter(
    private val onClick: (id: Int) -> Unit,
) : ListAdapter<EntityListItem, EntityListItemViewHolder>(EntityItemDiffCallback) {
    private companion object EntityItemDiffCallback : DiffUtil.ItemCallback<EntityListItem>() {
        override fun areItemsTheSame(oldItem: EntityListItem, newItem: EntityListItem): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: EntityListItem, newItem: EntityListItem): Boolean {
            return oldItem == newItem
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EntityListItemViewHolder {
        return EntityListItemViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: EntityListItemViewHolder, position: Int) {
        holder.bind(getItem(position), onClick)
    }
}

