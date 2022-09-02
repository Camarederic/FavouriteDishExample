package com.example.favouritedishexample.model.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.favouritedishexample.model.entities.FavouriteDish
import kotlinx.coroutines.flow.Flow


// 16.1) Создаем интерфейс dao
@Dao
interface FavouriteDishDao {

    // 16.2) Создаем метод
    @Insert
    suspend fun insertFavouriteDishDetails(favouriteDish: FavouriteDish)

    // 21.1) Создаем метод для получения всего списка еды
    @Query("SELECT * FROM FAVOURITE_DISHES_TABLE ORDER BY ID")
    fun getAllDishesList(): Flow<List<FavouriteDish>>

    // 29.1) Создаем метод для обновления
    @Update
    suspend fun updateFavouriteDishDetails(favouriteDish: FavouriteDish)

    // 30.1) Создаем метод для получения любимых блюд
    @Query("SELECT * FROM FAVOURITE_DISHES_TABLE WHERE favourite_dish = 1")
    fun getFavouriteDishesList():Flow<List<FavouriteDish>>
}