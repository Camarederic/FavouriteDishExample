package com.example.favouritedishexample.view.activities

import android.Manifest
import android.app.Activity
import android.app.AlertDialog
import android.app.Dialog
import android.content.ActivityNotFoundException
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Camera
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.provider.Settings
import android.view.View
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import com.example.favouritedishexample.R
import com.example.favouritedishexample.databinding.ActivityAddUpdateDishBinding
import com.example.favouritedishexample.databinding.DialogCustomImageSelectionBinding
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import com.karumi.dexter.listener.single.PermissionListener

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
                    //Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.CAMERA)
                .withListener(object : MultiplePermissionsListener {
                    override fun onPermissionsChecked(report: MultiplePermissionsReport?) {
                        // 9.2) Добавляем функцию let и помещаем туда код
                        report?.let {
                            // 8.4) Делаем проверку
                            if (report.areAllPermissionsGranted()) {
                                // 9.3) Удаляем тоаст и создаем интент
//                                Toast.makeText(this@AddUpdateDishActivity,
//                                    "You have camera permission now.",
//                                    Toast.LENGTH_SHORT).show()
                                val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                                startActivityForResult(intent, CAMERA)
                            } else {
                                // 8.8) Вызываем метод
                                showRationalDialogForPermissions()
                            }
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
                .withPermission(
                    //Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.READ_EXTERNAL_STORAGE
                )
                // 9.1) Изменяем код
                .withListener(object : PermissionListener {
                    override fun onPermissionGranted(p0: PermissionGrantedResponse?) {
                        Toast.makeText(this@AddUpdateDishActivity,
                            "You have the Gallery permission now to select image",
                            Toast.LENGTH_SHORT).show()
                    }

                    override fun onPermissionDenied(p0: PermissionDeniedResponse?) {
                        Toast.makeText(this@AddUpdateDishActivity,
                            "You have denied the storage permission to select image",
                            Toast.LENGTH_SHORT).show()
                    }

                    override fun onPermissionRationaleShouldBeShown(
                        p0: PermissionRequest?,
                        p1: PermissionToken?,
                    ) {
                        showRationalDialogForPermissions()
                    }
//                    override fun onPermissionsChecked(report: MultiplePermissionsReport?) {
////                        if (report!!.areAllPermissionsGranted())
////                            Toast.makeText(this@AddUpdateDishActivity,
////                                "You have the Gallery permission now to select image.",
////                            Toast.LENGTH_SHORT).show()
//                    }

//                    override fun onPermissionRationaleShouldBeShown(
//                        permissions: MutableList<PermissionRequest>?,
//                        token: PermissionToken?,
//                    ) {
//                        showRationalDialogForPermissions()
//                    }

                }).onSameThread().check()


            dialog.dismiss()
        }

        // 30) Показываем диалоговое окно
        dialog.show()
    }

    // 9.5) Создаем метод
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK){
            if (requestCode == CAMERA){
                data?.extras?.let {
                    val thumbnail: Bitmap = data.extras!!.get("data") as Bitmap
                    mBinding.imageViewDish.setImageBitmap(thumbnail)

                    // 9.6) Загружаем векторную картинку edit

                    // 9.7) Изменяем иконку фотоаппарата с плюсиком на карандаш
                    mBinding.imageViewAddDish.setImageDrawable(ContextCompat.getDrawable(this,
                    R.drawable.ic_vector_edit))
                }
            }
        }
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

    // 9.4) Создаем константу для камеры
    companion object {

        private const val CAMERA = 1
    }
}

// 11) В манифесте добавляем label И theme
// 12) Меняем название HomeFragment на AllDishesFragment и xml файл
// 13) Добавляем иконку плюс
// 14) Далее создаем в папку menu, menu_all_dishes

// 8.1) Добавляем разрешение в манифест
// 8.2) Добавляем библиотеку dexter в gradle