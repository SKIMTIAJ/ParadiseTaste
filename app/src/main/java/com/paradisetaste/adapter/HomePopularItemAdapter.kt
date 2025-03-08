package com.paradisetaste.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.paradisetaste.databinding.HomePopularItemBinding
import com.paradisetaste.models.Meal
import com.paradisetaste.utils.GlideUtils
import dagger.hilt.android.scopes.ActivityScoped
import javax.inject.Inject

@ActivityScoped
class HomePopularItemAdapter @Inject constructor():ListAdapter<Meal,HomePopularItemAdapter.ItemViewHolder>(ComparatorDiffUtils()) {

    var onItemClick : ((Meal)->Unit)? = null

    inner class ItemViewHolder(private val binding: HomePopularItemBinding):RecyclerView.ViewHolder(binding.root){
        fun bind(type:Meal){
            GlideUtils.loadImage(binding.popularItem,type.strMealThumb)
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): HomePopularItemAdapter.ItemViewHolder {
        val binding = HomePopularItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item = getItem(position)
        item?.let {
            holder.bind(it)
        }

        holder.itemView.setOnClickListener{
            onItemClick!!.invoke(getItem(position))
        }
    }

    class ComparatorDiffUtils: DiffUtil.ItemCallback<Meal>() {
        override fun areItemsTheSame(oldItem: Meal, newItem: Meal): Boolean {
            return oldItem.idMeal == newItem.idMeal
        }

        override fun areContentsTheSame(oldItem: Meal, newItem: Meal): Boolean {
            return oldItem == newItem
        }

    }

}