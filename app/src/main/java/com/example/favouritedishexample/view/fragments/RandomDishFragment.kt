package com.example.favouritedishexample.view.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.favouritedishexample.databinding.FragmentRandomDishBinding
import com.example.favouritedishexample.viewmodel.NotificationsViewModel
import com.example.favouritedishexample.viewmodel.RandomDishViewModel

class RandomDishFragment : Fragment() {

    // 39.4) Создаем binding
    private var mBinding: FragmentRandomDishBinding? = null

    // 44.1) Создаем viewModel
    private lateinit var mRandomDishViewModel: RandomDishViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {

        // 39.5) Инициализируем binding
        mBinding = FragmentRandomDishBinding.inflate(inflater, container, false)
        return mBinding!!.root
    }

    // 44.2) Создаем метод onViewCreated
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mRandomDishViewModel = ViewModelProvider(this).get(RandomDishViewModel::class.java)
        // 44.4)
        mRandomDishViewModel.getRandomRecipeFromAPI()

        // 44.9) Вызываем метод
        randomDishViewModelObserver()

    }

    // 44.5) Создаем функцию
    private fun randomDishViewModelObserver() {
        // 44.6) Создаем observe для randomDishResponse
        mRandomDishViewModel.randomDishResponse.observe(viewLifecycleOwner,
            { randomDishResponse ->
                randomDishResponse?.let {
                    Log.i("Random Dish Response", "$randomDishResponse.recipes[0]")
                }
            }
        )
        // 44.7) Создаем observe для randomDishLoadingError
        mRandomDishViewModel.randomDishLoadingError.observe(viewLifecycleOwner,
            { dataError ->
                dataError?.let {
                    Log.e("Random Dish API Error", "$dataError")
                }
            }
        )
        // 44.8) Создаем observe для loadRandomDish
        mRandomDishViewModel.loadRandomDish.observe(viewLifecycleOwner,
            { loadRandomDish ->
                loadRandomDish?.let {
                    Log.i("Random Dish Loading", "$loadRandomDish")
                }
            }
        )
    }

    // 39.6) Создаем метод onDestroy
    override fun onDestroy() {
        super.onDestroy()
        mBinding = null
    }

}
// 40.1) Дальше идем на сайт spoonacular.com/food-api и берем рецепты
// 40.2) Идем в settings -> plugins и устанавливаем плагин JSON TO Kotlin Class
// 40.3) Далее создаем в папке entities класс RandomDish