package com.example.favouritedishexample.model.network

import com.example.favouritedishexample.model.entities.RandomDish
import com.example.favouritedishexample.utils.Constants
import io.reactivex.rxjava3.core.Single
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

// 42.1) Создаем класс
class RandomDishApiService {

    // 42.5) Создаем api используя Retrofit
    private val api = Retrofit.Builder().baseUrl(Constants.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
        .build()
        .create(RandomDishAPI::class.java)

    // 42.6) Создаем функцию для получения случайных блюд
    fun getRandomDish(): Single<RandomDish.Recipes> {
        return api.getRandomDish(Constants.API_KEY_VALUE,
            Constants.LIMIT_LICENSE_VALUE,
            Constants.TAGS_VALUE,
            Constants.NUMBER_VALUE)
    }

}