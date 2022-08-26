package com.example.favouritedishexample.application

import android.app.Application
import com.example.favouritedishexample.model.database.FavouriteDishRepository
import com.example.favouritedishexample.model.database.FavouriteDishRoomDatabase

// 19.6) Создаем класс
class FavouriteDishApplication : Application() {

    // 19.7) Создаем базу данных и репозиторий по лени
    private val database by lazy { FavouriteDishRoomDatabase.getDatabase((this@FavouriteDishApplication)) }
    val repository by lazy { FavouriteDishRepository(database.favouriteDishDao()) }

    // 19.8) В манифесте в разделе application добавляем фразу name
}