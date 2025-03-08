package com.paradisetaste.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.paradisetaste.databinding.HomeCateroriesItemBinding
import com.paradisetaste.models.Category
import com.paradisetaste.utils.GlideUtils
import dagger.hilt.android.scopes.ActivityScoped
import javax.inject.Inject

@ActivityScoped
class HomeCategoriesAdapter @Inject constructor():ListAdapter<Category,HomeCategoriesAdapter.ItemViewholder>(CompareDiffUtils()) {

    var onItemClick:((Category)->Unit)?=null

    inner class ItemViewholder(private val binding:HomeCateroriesItemBinding):RecyclerView.ViewHolder(binding.root){
        fun bindData(type: Category){
            GlideUtils.loadImage(binding.categoryItemImage,type.strCategoryThumb)
            binding.categoryItemText.text = type.strCategory
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewholder {
        val binding = HomeCateroriesItemBinding.inflate(LayoutInflater.from(parent.context), parent,false)
        return ItemViewholder(binding)
    }

    override fun onBindViewHolder(holder: ItemViewholder, position: Int) {
        val itemAt = getItem(position)
        itemAt?.let {
            holder.bindData(it)
        }

        holder.itemView.setOnClickListener{
            onItemClick!!.invoke(getItem(position))
        }
    }

}

class CompareDiffUtils:DiffUtil.ItemCallback<Category>() {
    override fun areItemsTheSame(oldItem: Category, newItem: Category): Boolean {
        return oldItem.idCategory == newItem.idCategory
    }

    override fun areContentsTheSame(oldItem: Category, newItem: Category): Boolean {
        return oldItem == newItem
    }

}
