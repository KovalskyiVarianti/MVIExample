package com.example.mviexample.presentation.list.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mviexample.databinding.ItemEntityListBinding
import com.example.mviexample.presentation.list.EntityListItem

class EntityListItemViewHolder private constructor(
    private val binding: ItemEntityListBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: EntityListItem, onClick: (id: Int) -> Unit) = binding.apply {
        root.setOnClickListener { onClick(item.id) }
        name.text = item.name
        description.text = item.description
    }

    companion object {
        fun from(parent: ViewGroup) = EntityListItemViewHolder(
            ItemEntityListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }
}