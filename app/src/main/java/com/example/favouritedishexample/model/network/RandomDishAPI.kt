package com.example.favouritedishexample.model.network

import com.example.favouritedishexample.model.entities.RandomDish
import com.example.favouritedishexample.utils.Constants
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Query

// 41.2) Создаем в пакете network интерфейс
interface RandomDishAPI {

    // 41.3) Создаем функцию для получения блюд
    @GET(Constants.API_ENDPOINT)
    fun getRandomDish(

        // 41.5) Делаем запросы на наши параметры из сайта
    @Query(Constants.API_KEY) apiKey: String,
    @Query(Constants.LIMIT_LICENSE) limitLicense: Boolean,
    @Query(Constants.TAGS) tags: String,
    @Query(Constants.NUMBER) number: Int

    ):Single<RandomDish.Recipes>
}