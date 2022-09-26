package com.example.favouritedishexample.viewmodel

import androidx.lifecycle.MutableLiveData
import com.example.favouritedishexample.model.entities.RandomDish
import com.example.favouritedishexample.model.network.RandomDishApiService
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.observers.DisposableSingleObserver
import io.reactivex.rxjava3.schedulers.Schedulers

// 43.0) Добавляем библиотеку RxJava3 в gradle
// 43.1) Создаем класс
class RandomDishViewModel {

    // 43.2) Создаем объекты
    private val randomRecipeApiService = RandomDishApiService()
    private val compositeDisposable = CompositeDisposable()

    // 43.3) Создаем объекты MutableLiveData
    val loadRandomDish = MutableLiveData<Boolean>()
    val randomDishResponse = MutableLiveData<RandomDish.Recipes>()
    val randomDishLoadingError = MutableLiveData<Boolean>()

    // 43.4) Создаем функцию для получения случайного рецепта из API
    fun getRandomRecipeFromAPI(){
        loadRandomDish.value = true

        compositeDisposable.add(
            randomRecipeApiService.getRandomDish()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<RandomDish.Recipes>() {
                    override fun onSuccess(value: RandomDish.Recipes) {
                        loadRandomDish.value = false
                        randomDishResponse.value = value
                        randomDishLoadingError.value = false
                    }

                    override fun onError(e: Throwable) {
                        loadRandomDish.value = false
                        randomDishLoadingError.value = true
                        e.printStackTrace()
                    }

                })
        )
    }
}