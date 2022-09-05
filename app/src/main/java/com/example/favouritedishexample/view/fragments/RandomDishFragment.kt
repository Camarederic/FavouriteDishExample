package com.example.favouritedishexample.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.favouritedishexample.databinding.FragmentRandomDishBinding
import com.example.favouritedishexample.viewmodel.NotificationsViewModel

class RandomDishFragment : Fragment() {

    // 39.4) Создаем binding
    private var mBinding: FragmentRandomDishBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // 39.5) Инициализируем binding
        mBinding = FragmentRandomDishBinding.inflate(inflater,container,false)

        return mBinding!!.root
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