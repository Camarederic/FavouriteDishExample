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

    // 29.2) Создаем метод
    @WorkerThread
    suspend fun updateFavouriteDishData(favouriteDish: FavouriteDish){
        favouriteDishDao.updateFavouriteDishDetails(favouriteDish)
    }

    // 30.2) Создаем список для получения всей еды с дао
    val favouriteDishes: Flow<List<FavouriteDish>> = favouriteDishDao.getFavouriteDishesList()
}