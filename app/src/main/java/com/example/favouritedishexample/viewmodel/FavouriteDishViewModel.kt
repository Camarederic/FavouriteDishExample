package com.example.favouritedishexample.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.favouritedishexample.model.database.FavouriteDishRepository
import com.example.favouritedishexample.model.entities.FavouriteDish
import kotlinx.coroutines.launch

// 19.1) Создаем класс
class FavouriteDishViewModel(private val repository: FavouriteDishRepository) : ViewModel() {

    // 19.2) Создаем метод insert
    fun insert(dish:FavouriteDish) = viewModelScope.launch{
        repository.insertFavouriteDishData(dish)
    }
}

// 19.3) Создаем новый класс
class FavouriteDishViewModelFactory(private val repository: FavouriteDishRepository) : ViewModelProvider.Factory {

    // 19.4) Имплементируем метод
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FavouriteDishViewModel::class.java)){
            @Suppress("UNCHECKED_CAST")
            return FavouriteDishViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}

