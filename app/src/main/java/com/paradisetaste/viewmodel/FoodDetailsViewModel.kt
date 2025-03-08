package com.paradisetaste.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.paradisetaste.models.Meal
import com.paradisetaste.repositories.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FoodDetailsViewModel @Inject constructor(private val repository: Repository):ViewModel() {

    val foodDetailsLiveData = repository.foodDetailsLiveData

    fun getfoodDetailsItemFromHome(id:Int){
        viewModelScope.launch {
            repository.getFoorDetails(id)
        }
    }

    fun insetIntoDB(meal: Meal){
        try{
            viewModelScope.launch(Dispatchers.IO) {
                repository.insetMealDb(meal)
            }
        }catch (e:Exception){
          Log.d("FoodViewModel","Error in insertion ${e.message.toString()}")
        }
    }

    fun deleteFromDB(meal: Meal){
        viewModelScope.launch {
            repository.deleteMealDb(meal)
        }
    }

}