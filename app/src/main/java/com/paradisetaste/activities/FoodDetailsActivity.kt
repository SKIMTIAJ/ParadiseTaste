package com.paradisetaste.activities

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import com.paradisetaste.R
import com.paradisetaste.databinding.ActivityFoodDetailsBinding
import com.paradisetaste.fragments.home_fragments.HomeFragment
import com.paradisetaste.models.Meal
import com.paradisetaste.network.NetworkResult
import com.paradisetaste.utils.GlideUtils
import com.paradisetaste.viewmodel.FoodDetailsViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class FoodDetailsActivity : AppCompatActivity() {

    private val TAG = "FoodDetailsActivity"

    private var _binding:ActivityFoodDetailsBinding?=null
    private val binding get() = _binding!!
    private var dataForRoom: Meal? = null

    val viewModel by viewModels<FoodDetailsViewModel>()

    private lateinit var mealId:String
    private var youtubeLink:String = "null"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityFoodDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        getIntentExtras()
        loadInformationInView()
        itemClick()
    }

    private fun itemClick() {
        binding.apply {
            youtubeThumbnail.setOnClickListener{
                startActivity(Intent(Intent.ACTION_VIEW,Uri.parse(youtubeLink)))
            }
            favoriteFloatButton.setOnClickListener {
                dataForRoom?.let {
                    viewModel.insetIntoDB(it)
                    Toast.makeText(this@FoodDetailsActivity,
                        getString(R.string.added_to_favorites),Toast.LENGTH_SHORT).show()
                }
            }

        }

    }

    private fun loadInformationInView() {
        obserVeData(mealId)
    }

    private fun obserVeData(mealId: String) {
        val id = Integer.valueOf(mealId)
        viewModel.getfoodDetailsItemFromHome(id)

        viewModel.foodDetailsLiveData.observe(this){
            when(it){
                is NetworkResult.Success->{
                    Log.d(TAG,it.data?.meals.toString())
                    dataForRoom = it.data?.meals?.get(0)
                    val dataSource = it.data?.meals?.firstOrNull()!!
                    GlideUtils.loadImage(binding.imageOfMealDetails,dataSource?.strMealThumb)
                    binding.collapsibleToolBar.title = dataSource?.strMeal
                    binding.textCategory.text = "Category : ${dataSource?.strCategory}"
                    binding.textArea.text = "Area : ${dataSource?.strArea}"
                    binding.instructionDescription.text = dataSource?.strInstructions
                    this.youtubeLink = dataSource?.strYoutube!!
                    binding.linearProgressBar.visibility = View.GONE
                }
                is NetworkResult.Error ->{
                    Toast.makeText(this,it.message.toString(), Toast.LENGTH_SHORT).show()
                    binding.linearProgressBar.visibility = View.GONE
                }
                is NetworkResult.Loading->{
                    binding.linearProgressBar.visibility = View.VISIBLE
                }
            }
        }
    }

    private fun getIntentExtras() {
        val intent = intent
        mealId = intent.getStringExtra(HomeFragment.Meal_id)!!
    }


}