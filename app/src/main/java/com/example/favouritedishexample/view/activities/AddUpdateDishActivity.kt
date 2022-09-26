package com.example.favouritedishexample.view.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.favouritedishexample.R
import com.example.favouritedishexample.databinding.ActivityAddUpdateDishBinding

// 10) Создаем активити
class AddUpdateDishActivity : AppCompatActivity(), View.OnClickListener { // 21) Добавляем View.OnClickListener

    private lateinit var mBinding: ActivityAddUpdateDishBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityAddUpdateDishBinding.inflate(layoutInflater)
        setContentView(mBinding.root)

        // 25) Вызываем метод
        setupActionBar()

        // 23) Добавляем клик слушателя для картинки фото с плюсиком
        mBinding.imageViewAddDish.setOnClickListener(this)
    }

    // 24) Создаем метод для настройки тулбара для возврата назад
    private fun setupActionBar(){
        setSupportActionBar(mBinding.toolbarAddDishActivity)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        mBinding.toolbarAddDishActivity.setNavigationOnClickListener{
            onBackPressed()
        }
    }

    // 22) Добавляем метод onClick при нажатии на иконку фото с плюсиком
    override fun onClick(v: View?) {
        if (v != null) {
            when(v.id){
                R.id.imageViewAddDish ->{
                    Toast.makeText(this, "You have clicked the ImageView",
                        Toast.LENGTH_SHORT).show()
                    return
                }
            }
        }
    }
}

// 11) В манифесте добавляем label И theme
// 12) Меняем название HomeFragment на AllDishesFragment и xml файл
// 13) Добавляем иконку плюс
// 14) Далее создаем в папку menu, menu_all_dishes