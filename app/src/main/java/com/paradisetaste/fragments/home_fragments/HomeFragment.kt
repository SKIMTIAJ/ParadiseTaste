package com.paradisetaste.fragments.home_fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.paradisetaste.activities.CategoryDetailsActivity
import com.paradisetaste.activities.FoodDetailsActivity
import com.paradisetaste.adapter.HomeCategoriesAdapter
import com.paradisetaste.adapter.HomePopularItemAdapter
import com.paradisetaste.databinding.FragmentHomeBinding
import com.paradisetaste.models.Meal
import com.paradisetaste.network.NetworkResult
import com.paradisetaste.utils.GlideUtils
import com.paradisetaste.viewmodel.HomeMainViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class HomeFragment : Fragment() {
    private val TAG = "HomeFragment"
    private var _binding : FragmentHomeBinding?=null
    private val binding get() = _binding!!

    private lateinit var randomMeal: Meal

    companion object{
        const val Meal_id = "com.paradisetaste.fragments.home_fragments.idMeal"
        const val Meal_Name = "com.paradisetaste.fragments.home_fragments.nameMeal"
        const val Meal_thumb = "com.paradisetaste.fragments.home_fragments.thumbMeal"
        const val Item_Name = "com.paradisetaste.fragments.home_fragments.nameMeal.CategoryDetails"
    }


    lateinit var navController:NavController


    @Inject
    lateinit var popularItemAdapter:HomePopularItemAdapter
    @Inject
    lateinit var homeCategoryAdapter:HomeCategoriesAdapter

    val homeViewmodel by viewModels<HomeMainViewModel>()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentHomeBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = findNavController()
        randomFoodItem()
        initializingPopularItem()
        initializingCategoryItem()

        itemClick()
    }

    private fun itemClick() {
        binding.homeRandomFoodImage.setOnClickListener{
            val intent = Intent(activity,FoodDetailsActivity::class.java)
            intent.putExtra(Meal_id, randomMeal.idMeal)
            intent.putExtra(Meal_Name,randomMeal.strMeal)
            intent.putExtra(Meal_thumb,randomMeal.strMealThumb)
            startActivity(intent)
        }

        popularItemAdapter.onItemClick = { meal ->
            val intent = Intent(activity,FoodDetailsActivity::class.java)
            intent.putExtra(Meal_id, meal.idMeal)
            intent.putExtra(Meal_Name,meal.strMeal)
            intent.putExtra(Meal_thumb,meal.strMealThumb)
            startActivity(intent)
        }

        homeCategoryAdapter.onItemClick = { category ->
           // Toast.makeText(activity,category.strCategory,Toast.LENGTH_SHORT).show()
            val intenForCategoryDetails = Intent(activity,CategoryDetailsActivity::class.java)
            intenForCategoryDetails.putExtra(Item_Name,category.strCategory)
            startActivity(intenForCategoryDetails)
        }

    }

    private fun initializingCategoryItem() {
        binding.homeCategoryItemRecyclerView
        binding.homeCategoryItemRecyclerView.layoutManager = GridLayoutManager(requireActivity(),3,GridLayoutManager.VERTICAL,false)
        binding.homeCategoryItemRecyclerView.hasFixedSize()
        homeViewmodel.getCategoryFromHome()
        homeViewmodel.categoryLiveData.observe(requireActivity()){
            when(it){
                is NetworkResult.Success ->{
                    homeCategoryAdapter.submitList(it.data?.categories)
                    binding.homeCategoryItemRecyclerView.adapter = homeCategoryAdapter
                    binding.homeCategoryProgressBar.visibility = View.GONE
                }
                is NetworkResult.Error->{
                    Toast.makeText(requireActivity(),it.message.toString(),Toast.LENGTH_SHORT).show()
                    binding.homeCategoryProgressBar.visibility = View.GONE
                }
                is NetworkResult.Loading->{
                    binding.homeCategoryProgressBar.visibility = View.VISIBLE
                }
            }
        }
    }

    private fun initializingPopularItem() {
        binding.homePopularItemRecyclerView
        binding.homePopularItemRecyclerView.layoutManager = LinearLayoutManager(requireActivity(),LinearLayoutManager.HORIZONTAL,false)
        binding.homePopularItemRecyclerView.hasFixedSize()

        homeViewmodel.getFoodListFromHome("f")
        homeViewmodel.foodListLiveData.observe(requireActivity()){
            when(it){
                is NetworkResult.Success->{
                    popularItemAdapter.submitList(it.data?.meals)
                    binding.homePopularItemRecyclerView.adapter = popularItemAdapter
                    binding.homePopularFoodProgressBar.visibility = View.GONE
                }
                is NetworkResult.Error -> {
                    Toast.makeText(requireActivity(),it.message.toString(),Toast.LENGTH_SHORT).show()
                    binding.randomImageProgressBar.visibility = View.GONE
                }
                is NetworkResult.Loading ->{
                    binding.randomImageProgressBar.visibility = View.VISIBLE
                }
            }
        }
    }

    private fun randomFoodItem() {
       homeViewmodel.getRandomItemFromHome()
        homeViewmodel.randomFoodLiveData.observe(requireActivity()) { it ->
            when (it) {
                is NetworkResult.Success -> {
                    // Log.d(TAG,it.data?.meals?.map { it.strMealThumb }.toString())
                    val imageUrl = it.data?.meals?.firstOrNull()?.strMealThumb
                    GlideUtils.loadImage(binding.homeRandomFoodImage,imageUrl)
                    binding.randomImageProgressBar.visibility = View.GONE

                    this.randomMeal = it.data?.meals?.firstOrNull()!!
                }
                is NetworkResult.Error -> {
                   // Log.d(TAG, it.message.toString())
                    Toast.makeText(requireActivity(),it.message.toString(),Toast.LENGTH_SHORT).show()
                    binding.randomImageProgressBar.visibility = View.GONE
                }
                is NetworkResult.Loading -> {
                    binding.randomImageProgressBar.visibility = View.VISIBLE
                }
            }
        }
    }


}