package com.example.favouritedishexample.model.database

import androidx.annotation.WorkerThread
import com.example.favouritedishexample.model.entities.FavouriteDish
import kotlinx.coroutines.flow.Flow

// 18.1) Создаем класс репозитория
class FavouriteDishRepository(private val favouriteDishDao: FavouriteDishDao) {

    // 18.2) Создаем метод
    @WorkerThread
    suspend fun insertFavouriteDishData(favouriteDish: FavouriteDish){
        favouriteDishDao.insertFavouriteDishDetails(favouriteDish)
    }

    // 21.2) Создаем список всей еды с дао
    val allDishesList: Flow<List<FavouriteDish>> = favouriteDishDao.getAllDishesList()

    // 29.2) Создаем метод обновления
    @WorkerThread
    suspend fun updateFavouriteDishData(favouriteDish: FavouriteDish){
        favouriteDishDao.updateFavouriteDishDetails(favouriteDish)
    }

    // 30.2) Создаем список для получения всей еды с дао
    val favouriteDishes: Flow<List<FavouriteDish>> = favouriteDishDao.getFavouriteDishesList()

    // 35.2) Создаем метод для удаления
    @WorkerThread
    suspend fun deleteFavouriteDishData(favouriteDish: FavouriteDish){
        favouriteDishDao.deleteFavouriteDishDetails(favouriteDish)
    }

    // 38.2) Создаем метод для фильтрации списка блюд
    fun filteredListDishes(value: String): Flow<List<FavouriteDish>> = favouriteDishDao.getFilteredDishesList(value)
}