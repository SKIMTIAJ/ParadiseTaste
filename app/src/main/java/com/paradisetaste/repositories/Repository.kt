package com.paradisetaste.repositories

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.paradisetaste.db.Meal_DataBase
import com.paradisetaste.models.Category_details
import com.paradisetaste.models.FoodDetails
import com.paradisetaste.models.FoodListNameByA
import com.paradisetaste.models.Food_Categories
import com.paradisetaste.models.Meal
import com.paradisetaste.models.Random_meal
import com.paradisetaste.network.ApiCalls
import com.paradisetaste.network.NetworkResult
import org.json.JSONObject
import javax.inject.Inject

class Repository @Inject constructor(private val apiCalls: ApiCalls,private val mealDatabse: Meal_DataBase) {

    private var categoriesMutableLiveData = MutableLiveData<NetworkResult<Food_Categories>>()
    val categoriesLiveData:LiveData<NetworkResult<Food_Categories>> get() = categoriesMutableLiveData

    private var foodListMutableLiveData = MutableLiveData<NetworkResult<FoodListNameByA>>()
    val foodListLiveData:LiveData<NetworkResult<FoodListNameByA>> get() = foodListMutableLiveData

    private var randomMutableLiveData = MutableLiveData<NetworkResult<Random_meal>>()
    val randomLiveData:LiveData<NetworkResult<Random_meal>> get() = randomMutableLiveData

    private var foodDetailsMutableLiveData = MutableLiveData<NetworkResult<FoodDetails>>()
    val foodDetailsLiveData:LiveData<NetworkResult<FoodDetails>> get() = foodDetailsMutableLiveData

    private var categoryDetailsMutableLiveData = MutableLiveData<NetworkResult<Category_details>>()
    val categoryDetailsLiveData:LiveData<NetworkResult<Category_details>> get() = categoryDetailsMutableLiveData



    suspend fun getCatagoriesData(){
        categoriesMutableLiveData.postValue(NetworkResult.Loading())
        val result = apiCalls.getCategories()
        if (result.isSuccessful && result.body()!=null){
            categoriesMutableLiveData.postValue(NetworkResult.Success(result.body()))
        }else if (result.errorBody()!=null){
            val error = JSONObject(result.errorBody()!!.charStream().readText())
            val message = error.getString("message")
            categoriesMutableLiveData.postValue(NetworkResult.Error(message))
        }else{
            categoriesMutableLiveData.postValue(NetworkResult.Error("Somthing went wrong"))
        }
    }

    suspend fun getFoodListData(key:String){
        foodListMutableLiveData.postValue(NetworkResult.Loading())
        val result = apiCalls.getFoodList(key)
        if (result.isSuccessful && result.body()!=null){
            foodListMutableLiveData.postValue(NetworkResult.Success(result.body()))
        }else if (result.errorBody()!=null){
            val error = JSONObject(result.errorBody()!!.charStream().readText())
            val message = error.getString("message")
            foodListMutableLiveData.postValue(NetworkResult.Error(message))
        }else{
            foodListMutableLiveData.postValue(NetworkResult.Error("Somthing went wrong"))
        }
    }

    suspend fun getRandomData(){
        randomMutableLiveData.postValue(NetworkResult.Loading())
        val result = apiCalls.getRandomSingleMeal()
        if (result.isSuccessful && result.body()!=null){
            randomMutableLiveData.postValue(NetworkResult.Success(result.body()))
        }else if (result.errorBody()!=null){
            val error = JSONObject(result.errorBody()!!.charStream().readText())
            val message = error.getString("message")
            randomMutableLiveData.postValue(NetworkResult.Error(message))
        }else{
            randomMutableLiveData.postValue(NetworkResult.Error("Somthing went wrong"))
        }
    }

    suspend fun getFoorDetails(id:Int){
        val result = apiCalls.getFooddetails(id)
        if (result.isSuccessful && result.body()!=null){
            foodDetailsMutableLiveData.postValue(NetworkResult.Success(result.body()))
        }else if (result.errorBody()!=null){
            val error = JSONObject(result.errorBody()!!.charStream().readText())
            val message = error.getString("message")
            foodDetailsMutableLiveData.postValue(NetworkResult.Error(message))
        }else{
            foodDetailsMutableLiveData.postValue(NetworkResult.Error("Somthing went wrong"))
        }
    }

    suspend fun getCategoryDetails(type:String){
        val result = apiCalls.getCategoryDetails(type)
        if (result.isSuccessful && result.body()!=null){
            categoryDetailsMutableLiveData.postValue(NetworkResult.Success(result.body()))
        }else if (result.errorBody()!=null){
            val error = JSONObject(result.errorBody()!!.charStream().readText())
            val message = error.getString("message")
            categoryDetailsMutableLiveData.postValue(NetworkResult.Error(message))
        }else{
            categoryDetailsMutableLiveData.postValue(NetworkResult.Error("Somthing went wrong"))
        }
    }

    suspend fun insetMealDb(meal: Meal){
        try {
            mealDatabse.mealDao().upsert(meal)
        }catch (e:Exception){
            Log.d("FoodViewModel","Error in insertion ${e.message.toString()}")
        }
    }

    suspend fun deleteMealDb(meal: Meal){
        mealDatabse.mealDao().delete(meal)
    }

    fun favoriteMealLiveData(): LiveData<List<Meal>> {
        return mealDatabse.mealDao().getDataOfAllMeal()
    }

}