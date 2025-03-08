package com.paradisetaste.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.paradisetaste.databinding.CategoryDetailsItemBinding
import com.paradisetaste.models.MealList
import com.paradisetaste.utils.GlideUtils
import dagger.hilt.android.scopes.ActivityScoped
import javax.inject.Inject


@ActivityScoped
class CategoryDetailsAdapter @Inject constructor():ListAdapter<MealList, CategoryDetailsAdapter.CategoryDetailsViewHolder>(ComparetorDiffUtils()) {

    var onItemClick:((MealList)->Unit)?=null

    inner class CategoryDetailsViewHolder(private val binding:CategoryDetailsItemBinding): RecyclerView.ViewHolder(binding.root){

        fun binding(type: MealList){
            binding.categoryDetailsImageTitle.text = type.strMeal
            GlideUtils.loadImage(binding.categoryDetailsImageItem,type.strMealThumb)
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CategoryDetailsAdapter.CategoryDetailsViewHolder {
        val binding = CategoryDetailsItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return CategoryDetailsViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: CategoryDetailsAdapter.CategoryDetailsViewHolder,
        position: Int
    ) {
        val item = getItem(position)
        item?.let {
            holder.binding(it)
        }

        holder.itemView.setOnClickListener{
            onItemClick?.invoke(item)
        }
    }

    class ComparetorDiffUtils: DiffUtil.ItemCallback<MealList>() {
        override fun areItemsTheSame(oldItem: MealList, newItem: MealList): Boolean {
            return oldItem.idMeal == newItem.idMeal
        }

        override fun areContentsTheSame(oldItem: MealList, newItem: MealList): Boolean {
            return oldItem == newItem
        }

    }


}