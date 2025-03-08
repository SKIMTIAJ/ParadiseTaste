package com.paradisetaste.db

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import java.util.concurrent.Executors
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class DataBaseModule {

    @Provides
    fun provideMealDao(mealDatabse: Meal_DataBase):MealDao{
        return mealDatabse.mealDao()
    }

    @Singleton
    @Provides
    fun roomDatabaseProvider(@ApplicationContext context:Context):Meal_DataBase{
        return Room.databaseBuilder(context,Meal_DataBase::class.java,"MealDataBase")
            .fallbackToDestructiveMigration().build()
    }

}