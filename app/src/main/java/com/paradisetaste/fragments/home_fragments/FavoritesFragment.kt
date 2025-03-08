package com.paradisetaste.fragments.home_fragments

import android.content.Intent
import android.health.connect.datatypes.units.Length
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.paradisetaste.R
import com.paradisetaste.activities.FoodDetailsActivity
import com.paradisetaste.adapter.FavoriteListAdapter
import com.paradisetaste.databinding.FragmentFavoritesBinding
import com.paradisetaste.fragments.home_fragments.HomeFragment.Companion.Meal_Name
import com.paradisetaste.fragments.home_fragments.HomeFragment.Companion.Meal_id
import com.paradisetaste.fragments.home_fragments.HomeFragment.Companion.Meal_thumb
import com.paradisetaste.models.Meal
import com.paradisetaste.viewmodel.HomeMainViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class FavoritesFragment : Fragment() {
    private var _binding : FragmentFavoritesBinding?=null
    private val binding get() = _binding!!

    @Inject
    lateinit var adapter:FavoriteListAdapter

    val viewModel by viewModels<HomeMainViewModel>()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentFavoritesBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            favoriteProgressBar.visibility = View.VISIBLE
            favoriteItemRecyclerView.layoutManager = GridLayoutManager(requireActivity(),2,GridLayoutManager.VERTICAL,false)
            favoriteItemRecyclerView.hasFixedSize()
        }

        observeData()
        onItemClick()
        val itemTouchHelper = swipeDelete()
        ItemTouchHelper(itemTouchHelper).attachToRecyclerView(binding.favoriteItemRecyclerView)

    }

    private fun swipeDelete(): ItemTouchHelper.SimpleCallback {
        return object : ItemTouchHelper.SimpleCallback(
            ItemTouchHelper.UP or ItemTouchHelper.DOWN,
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return true
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                val itemMeal = adapter.currentList[position]
                viewModel.deleteFavoriteItem(itemMeal)
                val snakbar = Snackbar.make(requireView(), "Meal Deleted", Snackbar.LENGTH_SHORT)
                snakbar.setActionTextColor(resources.getColor(R.color.accent))
                snakbar.setAction("UNDO"){
                    viewModel.insetIntoDB(itemMeal)
                    setVisibilityForNoDataFound()
                }
                snakbar.show()
                setVisibilityForNoDataFound()
            }

            private fun setVisibilityForNoDataFound() {
                if (adapter.itemCount != 0 ) {
                    binding.onDataFound.visibility = View.GONE
                } else {
                    binding.onDataFound.visibility = View.VISIBLE
                }
            }

        }
    }


    private fun onItemClick() {
       adapter.itemClick={meal ->
           val intent = Intent(activity, FoodDetailsActivity::class.java)
           intent.putExtra(Meal_id, meal.idMeal)
           intent.putExtra(Meal_Name,meal.strMeal)
           intent.putExtra(Meal_thumb,meal.strMealThumb)
           startActivity(intent)
       }
    }

    private fun observeData() {
       viewModel.getFavoriteMealList().observe(requireActivity()){it->
           adapter.submitList(it)
           binding.favoriteItemRecyclerView.adapter = adapter
           binding.favoriteProgressBar.visibility = View.GONE
       }
    }


}