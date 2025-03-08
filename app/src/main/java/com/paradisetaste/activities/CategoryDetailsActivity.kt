package com.paradisetaste.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.paradisetaste.adapter.CategoryDetailsAdapter
import com.paradisetaste.databinding.ActivityCetagoryDetailsBinding
import com.paradisetaste.fragments.home_fragments.HomeFragment
import com.paradisetaste.network.NetworkResult
import com.paradisetaste.viewmodel.CategoryViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class CategoryDetailsActivity : AppCompatActivity() {

    private var _binding:ActivityCetagoryDetailsBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var adapter : CategoryDetailsAdapter

    val categoryDetailsViewModel by viewModels<CategoryViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
       // enableEdgeToEdge()
        _binding = ActivityCetagoryDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val categoryInten = intent.getStringExtra(HomeFragment.Item_Name)
        initialization(categoryInten)
        onClickItem()
    }

    private fun onClickItem() {
        adapter.onItemClick = { meal->
            val detaislIntent = Intent(this,FoodDetailsActivity::class.java)
            detaislIntent.putExtra(HomeFragment.Meal_id,meal.idMeal)
            startActivity(detaislIntent)
        }
    }

    private fun initialization(categoryInten: String?) {
        binding.cetagoryDetailsRecyclerView.layoutManager = GridLayoutManager(this,2,GridLayoutManager.VERTICAL,false)
        binding.cetagoryDetailsRecyclerView.hasFixedSize()
        categoryDetailsViewModel.getCategoryDetails(categoryInten!!)

        categoryDetailsViewModel.categoryDetailsLiveData.observe(this){
            when(it){
                is NetworkResult.Success->{
                    adapter.submitList(it.data?.meals)
                    binding.itemCount.text = it.data?.meals?.size.toString()
                    binding.cetagoryDetailsRecyclerView.adapter = adapter
                    binding.categoryDetailsProgressBar.visibility = View.GONE
                }
                is NetworkResult.Error->{
                    Toast.makeText(this,it.message.toString(), Toast.LENGTH_SHORT).show()
                    binding.categoryDetailsProgressBar.visibility = View.GONE
                }
                is NetworkResult.Loading->{
                    binding.categoryDetailsProgressBar.visibility = View.VISIBLE
                }
            }
        }
    }
}