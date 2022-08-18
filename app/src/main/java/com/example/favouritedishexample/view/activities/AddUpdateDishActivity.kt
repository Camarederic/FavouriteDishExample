package com.example.favouritedishexample.view.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.favouritedishexample.R

// 10) Создаем активити
class AddUpdateDishActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_update_dish)
    }
}

// 11) В манифесте добавляем label И theme
// 12) Меняем название HomeFragment на AllDishesFragment и xml файл
// 13) Добавляем иконку плюс
// 14) Далее создаем в папку menu, menu_all_dishes