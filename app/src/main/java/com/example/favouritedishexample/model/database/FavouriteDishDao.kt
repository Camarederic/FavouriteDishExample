package com.example.favouritedishexample.model.database

import androidx.room.*
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

    // 35.1) Создаем метод для удаления любимых блюд
    @Delete
    suspend fun deleteFavouriteDishDetails(favouriteDish: FavouriteDish)

    // 38.1) Создаем метод для получения фильтрации списка блюд
    @Query("SELECT * FROM FAVOURITE_DISHES_TABLE WHERE type = :filterType")
    fun getFilteredDishesList(filterType: String): Flow<List<FavouriteDish>>
}