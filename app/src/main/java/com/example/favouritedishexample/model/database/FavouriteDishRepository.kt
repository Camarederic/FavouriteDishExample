package com.example.favouritedishexample.model.database

import androidx.annotation.WorkerThread
import com.example.favouritedishexample.model.entities.FavouriteDish

// 18.1) Создаем класс репозитория
class FavouriteDishRepository(private val favouriteDishDao: FavouriteDishDao) {

    // 18.2) Создаем метод
    @WorkerThread
    suspend fun insertFavouriteDishData(favouriteDish: FavouriteDish){
        favouriteDishDao.insertFavouriteDishDetails(favouriteDish)

    }

}