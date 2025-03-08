package com.paradisetaste.network

import com.paradisetaste.models.Category_details
import com.paradisetaste.models.FoodDetails
import com.paradisetaste.models.FoodListNameByA
import com.paradisetaste.models.Food_Categories
import com.paradisetaste.models.Food_Details
import com.paradisetaste.models.Random_meal
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiCalls {

    @GET("random.php")
    suspend fun getRandomSingleMeal():Response<Random_meal>

    @GET("search.php")
    suspend fun getFoodList(@Query("f") key:String):Response<FoodListNameByA>

    @GET("categories.php")
    suspend fun getCategories():Response<Food_Categories>

    @GET("lookup.php")
    suspend fun getFooddetails(@Query("i") id:Int):Response<FoodDetails>

    @GET("filter.php")
    suspend fun getCategoryDetails(@Query("c") type:String):Response<Category_details>

}