package com.paradisetaste.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.paradisetaste.models.Meal

@Database(entities = [Meal::class], version = 2, exportSchema = true)
@TypeConverters(MealTypeConverter::class)
abstract class Meal_DataBase(): RoomDatabase() {
    abstract fun mealDao():MealDao
}