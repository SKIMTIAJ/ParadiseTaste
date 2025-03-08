package com.paradisetaste.utils

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

object ConstantClass {

    val BASE_URL = "https://www.themealdb.com/api/json/v1/1/"

    val MIGRATION_1_2 = object : Migration(1, 2) {
        override fun migrate(database: SupportSQLiteDatabase) {
            database.execSQL("ALTER TABLE mealInformation ADD COLUMN age INTEGER NOT NULL DEFAULT 0")
        }
    }

}