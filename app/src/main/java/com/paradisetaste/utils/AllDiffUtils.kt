package com.paradisetaste.utils

import androidx.recyclerview.widget.DiffUtil
import com.paradisetaste.models.Category

object AllDiffUtils: DiffUtil.ItemCallback<Category>() {
    override fun areItemsTheSame(oldItem: Category, newItem: Category): Boolean {
        return oldItem.idCategory == newItem.idCategory
    }

    override fun areContentsTheSame(oldItem: Category, newItem: Category): Boolean {
        return oldItem == newItem
    }

}
