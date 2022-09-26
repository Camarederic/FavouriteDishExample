package com.example.favouritedishexample.model.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.favouritedishexample.model.entities.FavouriteDish
import kotlin.synchronized as synchronized1

// 17.1) Создаем класс
@Database(entities = [FavouriteDish::class], version = 1)
abstract class FavouriteDishRoomDatabase : RoomDatabase() {

    // 19.5) Создаем метод дао
    abstract fun favouriteDishDao(): FavouriteDishDao

    // 17.2) Создаем метод
    companion object {

        @Volatile
        private var INSTANCE: FavouriteDishRoomDatabase? = null

        fun getDatabase(context: Context): FavouriteDishRoomDatabase {
            return INSTANCE ?: synchronized1(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    FavouriteDishRoomDatabase::class.java,
                    "favourite_dish_database"
                ).fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                instance

            }
        }
    }
}