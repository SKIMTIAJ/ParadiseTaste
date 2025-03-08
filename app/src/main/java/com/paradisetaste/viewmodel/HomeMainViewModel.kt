package com.paradisetaste.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.paradisetaste.models.Meal
import com.paradisetaste.network.NetworkResult
import com.paradisetaste.repositories.Repository
import dagger.hilt.android.ViewModelLifecycle
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeMainViewModel @Inject constructor(private val repository: Repository):ViewModel() {

    val randomFoodLiveData = repository.randomLiveData
    val categoryLiveData = repository.categoriesLiveData
    val foodListLiveData = repository.foodListLiveData
    val categoryDetailsLiveData = repository.categoryDetailsLiveData
    //val favoriteFoodLivedata:NetworkResult<LiveData<>>

    //...............

    val sharedData = MutableLiveData<String>()

    fun getCategoryFromHome(){
        viewModelScope.launch {
            repository.getCatagoriesData()
        }
    }

    fun getFoodListFromHome(key:String){
        viewModelScope.launch {
            repository.getFoodListData(key)
        }
    }

    fun getRandomItemFromHome(){
        viewModelScope.launch {
            repository.getRandomData()
        }
    }


    fun getCategoryDetails(type:String){
        viewModelScope.launch {
            repository.getCategoryDetails(type)
        }
    }


    //.......................................  ROOM  ................................


    fun getFavoriteMealList(): LiveData<List<Meal>>{
        return repository.favoriteMealLiveData()
    }

    fun deleteFavoriteItem(meal: Meal){
        viewModelScope.launch {
            repository.deleteMealDb(meal)
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

    //...................................................

    fun setData(data: String) {
        sharedData.value = data
    }

    fun getData(): LiveData<String> = sharedData

}