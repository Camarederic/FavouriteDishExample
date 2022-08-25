package com.example.favouritedishexample.model.database

import androidx.room.Dao
import androidx.room.Insert
import com.example.favouritedishexample.model.entities.FavouriteDish

// 16.1) Создаем интерфейс dao
@Dao
interface FavouriteDishDao {

    // 16.2) Создаем метод
    @Insert
    suspend fun insertFavouriteDishDetails(favouriteDish: FavouriteDish)
}