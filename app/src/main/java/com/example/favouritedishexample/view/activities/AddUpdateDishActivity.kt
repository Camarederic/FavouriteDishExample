package com.example.favouritedishexample.view.activities

import android.Manifest
import android.app.AlertDialog
import android.app.Dialog
import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.view.View
import android.widget.Toast
import com.example.favouritedishexample.R
import com.example.favouritedishexample.databinding.ActivityAddUpdateDishBinding
import com.example.favouritedishexample.databinding.DialogCustomImageSelectionBinding
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener

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
            //Toast.makeText(this, "Camera Clicked", Toast.LENGTH_SHORT).show()

            // 8.3) Применяем разрешения при помощи библиотеки Dexter для камеры
            Dexter.withContext(this)
                .withPermissions(
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.CAMERA)
                .withListener(object : MultiplePermissionsListener {
                    override fun onPermissionsChecked(report: MultiplePermissionsReport?) {
                        // 8.4) Делаем проверку
                        if (report!!.areAllPermissionsGranted()) {
                            Toast.makeText(this@AddUpdateDishActivity,
                                "You have camera permission now.", Toast.LENGTH_SHORT).show()
                        } else {
                            // 8.8) Вызываем метод
                            showRationalDialogForPermissions()
                        }
                    }

                    override fun onPermissionRationaleShouldBeShown(
                        // 8.5) Изменяем названия на permissions и token
                        permissions: MutableList<PermissionRequest>?,
                        token: PermissionToken?,
                    ) {
                        // 8.7) Вызываем метод
                        showRationalDialogForPermissions()
                    }

                }).onSameThread().check()


            dialog.dismiss()
        }

        binding.textViewGallery.setOnClickListener {
            //Toast.makeText(this, "Gallery Clicked", Toast.LENGTH_SHORT).show()

            // 8.9) Применяем разрешения при помощи библиотеки Dexter для галереи
            Dexter.withContext(this)
                .withPermissions(
                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.READ_EXTERNAL_STORAGE
                )
                .withListener(object : MultiplePermissionsListener {
                    override fun onPermissionsChecked(report: MultiplePermissionsReport?) {
                        if (report!!.areAllPermissionsGranted())
                            Toast.makeText(this@AddUpdateDishActivity,
                                "You have the Gallery permission now to select image.",
                            Toast.LENGTH_SHORT).show()
                    }

                    override fun onPermissionRationaleShouldBeShown(
                        permissions: MutableList<PermissionRequest>?,
                        token: PermissionToken?,
                    ) {
                        showRationalDialogForPermissions()
                    }

                }).onSameThread().check()


            dialog.dismiss()
        }

        // 30) Показываем диалоговое окно
        dialog.show()
    }

    // 8.6) Создаем метод для показа диалогового окна для разрешений
    private fun showRationalDialogForPermissions() {
        AlertDialog.Builder(this).setMessage("It Looks like you have turned off permissions" +
                " required for this feature. It can be enabled under Application Settings")
            .setPositiveButton("GO TO SETTINGS") { _, _ ->
                try {
                    val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                    val uri = Uri.fromParts("package", packageName, null)
                    intent.data = uri
                    startActivity(intent)
                } catch (e: ActivityNotFoundException) {
                    e.printStackTrace()
                }
            }
            .setNegativeButton("Cancel") { dialog, _ ->
                dialog.dismiss()
            }.show()
    }
}

// 11) В манифесте добавляем label И theme
// 12) Меняем название HomeFragment на AllDishesFragment и xml файл
// 13) Добавляем иконку плюс
// 14) Далее создаем в папку menu, menu_all_dishes

// 8.1) Добавляем разрешение в манифест
// 8.2) Добавляем библиотеку dexter в gradle