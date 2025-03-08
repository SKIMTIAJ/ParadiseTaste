package com.paradisetaste.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.paradisetaste.databinding.FavoriteItemBinding
import com.paradisetaste.models.Meal
import com.paradisetaste.utils.GlideUtils
import javax.inject.Inject

class FavoriteListAdapter @Inject constructor():ListAdapter<Meal,FavoriteListAdapter.FavoriteViewHolder>(CoparatorDiffUtils()) {

    var itemClick:((Meal)->Unit)?=null

    inner class FavoriteViewHolder(private val binding:FavoriteItemBinding):RecyclerView.ViewHolder(binding.root){
        fun binding(itemType:Meal){
            binding.favoriteItemName.text = itemType.strMeal.toString()
            GlideUtils.loadImage(binding.favoriteItemImage,itemType.strMealThumb)
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): FavoriteListAdapter.FavoriteViewHolder {
        val binding = FavoriteItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return FavoriteViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FavoriteListAdapter.FavoriteViewHolder, position: Int) {
        val item = getItem(position)
        item?.let {
            holder.binding(item)
        }
        holder.itemView.setOnClickListener{
            itemClick!!.invoke(item)
        }
    }
}

class CoparatorDiffUtils:DiffUtil.ItemCallback<Meal>() {
    override fun areItemsTheSame(oldItem: Meal, newItem: Meal): Boolean {
        return oldItem.strMeal == newItem.idMeal
    }

    override fun areContentsTheSame(oldItem: Meal, newItem: Meal): Boolean {
        return oldItem == newItem
    }

}
