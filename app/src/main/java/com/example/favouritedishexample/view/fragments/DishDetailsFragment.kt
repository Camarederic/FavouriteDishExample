package com.example.favouritedishexample.view.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.favouritedishexample.R



// 24.1) Создаем новый фрагмент
class DishDetailsFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {

        return inflater.inflate(R.layout.fragment_dish_details, container, false)
    }
}
// 24.2) Добавляем плагин safeargs в gradle(Module)
// Также добавляем версию плагина safeargs в gradle(Project)