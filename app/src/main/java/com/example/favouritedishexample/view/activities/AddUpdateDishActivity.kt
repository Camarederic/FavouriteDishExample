package com.example.favouritedishexample.view.activities

import android.app.Dialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.favouritedishexample.R
import com.example.favouritedishexample.databinding.ActivityAddUpdateDishBinding
import com.example.favouritedishexample.databinding.DialogCustomImageSelectionBinding

// 10) Создаем активити
class AddUpdateDishActivity : AppCompatActivity(),
    View.OnClickListener { // 21) Добавляем View.OnClickListener

    private lateinit var mBinding: ActivityAddUpdateDishBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityAddUpdateDishBinding.inflate(layoutInflater)
        setContentView(mBinding.root)

        // 25) Вызываем метод
        setupActionBar()

        // 26) Создаем layout под названием dialog_custom_image_selection для камеры и фото

        // 23) Добавляем клик слушателя для картинки фото с плюсиком
        mBinding.imageViewAddDish.setOnClickListener(this)
    }

    // 24) Создаем метод для настройки тулбара для возврата назад
    private fun setupActionBar() {
        setSupportActionBar(mBinding.toolbarAddDishActivity)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        mBinding.toolbarAddDishActivity.setNavigationOnClickListener {
            onBackPressed()
        }
    }

    // 22) Добавляем метод onClick при нажатии на иконку фото с плюсиком
    override fun onClick(v: View?) {
        if (v != null) {
            when (v.id) {
                R.id.imageViewAddDish -> {
//                    Toast.makeText(this, "You have clicked the ImageView",
//                        Toast.LENGTH_SHORT).show()
                    // 29) Вызываем метод
                    customImageSelectionDialog()

                    return
                }
            }
        }
    }

    // 27) Создаем метод для выбора в диалоговом окне пользовательского изображения
    private fun customImageSelectionDialog() {
        // 28) Создаем объект Dialog
        val dialog = Dialog(this)
        val binding: DialogCustomImageSelectionBinding =
            DialogCustomImageSelectionBinding.inflate(layoutInflater)
        dialog.setContentView(binding.root)

        // 31) Создаем клики слушателей на камеру и пфллерею
        binding.textViewCamera.setOnClickListener {
            Toast.makeText(this, "Camera Clicked", Toast.LENGTH_SHORT).show()
            dialog.dismiss()
        }
        binding.textViewGallery.setOnClickListener {
            Toast.makeText(this, "Gallery Clicked", Toast.LENGTH_SHORT).show()
            dialog.dismiss()
        }

        // 30) Показываем диалоговое окно
        dialog.show()
    }
}

// 11) В манифесте добавляем label И theme
// 12) Меняем название HomeFragment на AllDishesFragment и xml файл
// 13) Добавляем иконку плюс
// 14) Далее создаем в папку menu, menu_all_dishes