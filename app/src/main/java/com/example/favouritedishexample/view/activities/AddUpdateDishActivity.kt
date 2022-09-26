package com.example.favouritedishexample.view.activities

import android.Manifest
import android.app.Activity
import android.app.AlertDialog
import android.app.Dialog
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.ContextWrapper
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.provider.Settings
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.toBitmap
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.example.favouritedishexample.R
import com.example.favouritedishexample.application.FavouriteDishApplication
import com.example.favouritedishexample.databinding.ActivityAddUpdateDishBinding
import com.example.favouritedishexample.databinding.DialogCustomImageSelectionBinding
import com.example.favouritedishexample.databinding.DialogCustomListBinding
import com.example.favouritedishexample.model.entities.FavouriteDish
import com.example.favouritedishexample.utils.Constants
import com.example.favouritedishexample.view.adapters.CustomListItemAdapter
import com.example.favouritedishexample.viewmodel.FavouriteDishViewModel
import com.example.favouritedishexample.viewmodel.FavouriteDishViewModelFactory
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import com.karumi.dexter.listener.single.PermissionListener
import java.io.*
import java.util.*

// 10) Создаем активити
class AddUpdateDishActivity : AppCompatActivity(),
    View.OnClickListener { // 21) Добавляем View.OnClickListener

    private lateinit var mBinding: ActivityAddUpdateDishBinding

    // 12.3) Создаем переменную для пути картинки
    private var mImagePath: String = ""

    // 14.1) Создаем переменную
    private lateinit var mCustomListDialog: Dialog

    // 34.3) Создаем переменную
    private var mFavouriteDishDetails: FavouriteDish? = null

    // 20.1) Создаем viewModel
    private val mFavouriteDishViewModel: FavouriteDishViewModel by viewModels {
        FavouriteDishViewModelFactory((application as FavouriteDishApplication).repository)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        mBinding = ActivityAddUpdateDishBinding.inflate(layoutInflater)
        setContentView(mBinding.root)

        // 34.4) Создаем проверку
        if (intent.hasExtra(Constants.EXTRA_DISH_DETAILS)) {
            mFavouriteDishDetails = intent.getParcelableExtra(Constants.EXTRA_DISH_DETAILS)
        }

        // 25) Вызываем метод
        setupActionBar()

        // 34.6) Обновляем картинку и все компоненты
        mFavouriteDishDetails?.let {
            if (it.id != 0){
                mImagePath = it.image
                Glide.with(this@AddUpdateDishActivity)
                    .load(mImagePath)
                    .centerCrop()
                    .into(mBinding.imageViewDish)
                mBinding.editTextTitle.setText(it.title)
                mBinding.editTextType.setText(it.type)
                mBinding.editTextCategory.setText(it.category)
                mBinding.editTextIngredients.setText(it.ingredients)
                mBinding.editTextCookingTime.setText(it.cookingTime)
                mBinding.editTextDirection.setText(it.directionToCook)
                mBinding.buttonAddDish.text = resources.getString(R.string.label_update_dish)
            }
        }

        // 26) Создаем layout под названием dialog_custom_image_selection для камеры и фото

        // 23) Добавляем клик слушателя для картинки фото с плюсиком
        mBinding.imageViewAddDish.setOnClickListener(this)

        // 13.15) Добавляем клики слушателя для выбора типов еды, категории и времени приготовления
        mBinding.editTextType.setOnClickListener(this)
        mBinding.editTextCategory.setOnClickListener(this)
        mBinding.editTextCookingTime.setOnClickListener(this)

        // 14.9) Создаем клик слушателя для кнопки
        mBinding.buttonAddDish.setOnClickListener(this)
    }

    // 24) Создаем метод для настройки тулбара для возврата назад
    private fun setupActionBar() {
        setSupportActionBar(mBinding.toolbarAddDishActivity)

        // 34.5) Проверяем на null
        if (mFavouriteDishDetails != null && mFavouriteDishDetails!!.id != 0){
            supportActionBar?.let {
                it.title = resources.getString(R.string.title_edit_dish)
            }
        }else{
            supportActionBar?.let {
                it.title = resources.getString(R.string.title_add_dish)
            }
        }
        // 24)
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
                // 13.16) Добавляем id типа, категории и времени приготовления
                R.id.editTextType -> {
                    customItemsListDialog(resources.getString(R.string.title_select_dish_type),
                        Constants.dishTypes(),
                        Constants.DISH_TYPE)
                    return
                }
                R.id.editTextCategory -> {
                    customItemsListDialog(resources.getString(R.string.title_select_dish_category),
                        Constants.dishCategories(),
                        Constants.DISH_CATEGORY)
                    return
                }
                R.id.editTextCookingTime -> {
                    customItemsListDialog(resources.getString(R.string.title_select_dish_cooking_time),
                        Constants.dishCookTime(),
                        Constants.DISH_COOKING_TIME)
                    return
                }
                // 14.7) Создаем id для кнопки при нажатии на которую будут создаваться все пункты
                R.id.button_add_dish -> {
                    val title = mBinding.editTextTitle.text.toString().trim { it <= ' ' }
                    val type = mBinding.editTextType.text.toString().trim { it <= ' ' }
                    val category = mBinding.editTextCategory.text.toString().trim { it <= ' ' }
                    val ingredients =
                        mBinding.editTextIngredients.text.toString().trim { it <= ' ' }
                    val cookingTimeInMinutes =
                        mBinding.editTextCookingTime.toString().trim { it <= ' ' }
                    val cookingDirection = mBinding.editTextDirection.toString().trim { it <= ' ' }

                    // 14.8) Если путь картинки пустой, то создаем тоаст сообщение
                    // и к остальным пунктам
                    when {
                        TextUtils.isEmpty(mImagePath) -> {
                            Toast.makeText(this,
                                resources.getString(R.string.error_msg_select_dish_image),
                                Toast.LENGTH_SHORT).show()
                        }
                        TextUtils.isEmpty(title) -> {
                            Toast.makeText(this,
                                resources.getString(R.string.error_msg_enter_dish_title),
                                Toast.LENGTH_SHORT).show()
                        }
                        TextUtils.isEmpty(type) -> {
                            Toast.makeText(this,
                                resources.getString(R.string.error_msg_select_dish_type),
                                Toast.LENGTH_SHORT).show()
                        }
                        TextUtils.isEmpty(category) -> {
                            Toast.makeText(this,
                                resources.getString(R.string.error_msg_select_dish_category),
                                Toast.LENGTH_SHORT).show()
                        }
                        TextUtils.isEmpty(ingredients) -> {
                            Toast.makeText(this,
                                resources.getString(R.string.error_msg_enter_dish_ingredients),
                                Toast.LENGTH_SHORT).show()
                        }
                        TextUtils.isEmpty(cookingTimeInMinutes) -> {
                            Toast.makeText(this,
                                resources.getString(R.string.error_msg_select_dish_cooking_time),
                                Toast.LENGTH_SHORT).show()
                        }
                        TextUtils.isEmpty(cookingDirection) -> {
                            Toast.makeText(this,
                                resources.getString(R.string.error_msg_enter_dish_cooking_instructions),
                                Toast.LENGTH_SHORT).show()
                        }
                        else -> {

                            // 34.7)
                            var dishId = 0
                            var imageSource = Constants.DISH_IMAGE_SOURCE_LOCAL
                            var favouriteDish = false
                            mFavouriteDishDetails?.let {
                                if (it.id != 0){
                                    dishId = it.id
                                    imageSource = it.imageSource
                                    favouriteDish = it.favouriteDish
                                }
                            }


                            // 20.3) Удаляем тоаст сообщение и записываем вместо него другой код
                            // Toast.makeText(this, "All the entries are valid", Toast.LENGTH_SHORT)
                            // .show()
                            val favouriteDishDetails: FavouriteDish = FavouriteDish(
                                mImagePath,

                                // Constants.DISH_IMAGE_SOURCE_LOCAL, 34.10) Заменяем на imageSource
                                imageSource,
                                title,
                                type,
                                category,
                                ingredients,
                                cookingTimeInMinutes,
                                cookingDirection,
                                //false // 34.11) Удаляем false и добавляем favouriteDish и dishId
                            favouriteDish,
                                dishId
                            )

                            // 34.8)
                            if (dishId == 0){
                                // 34.9) Переносим этот код сюда
                                mFavouriteDishViewModel.insert(favouriteDishDetails)
                                // 20.5) Пишем другое тоаст сообщение
                                Toast.makeText(this@AddUpdateDishActivity,
                                    "You successfully added your favourite dish details.",
                                    Toast.LENGTH_SHORT).show()
                                Log.i("Insertion", "Success")

                                // 34.12)
                            }else{
                                mFavouriteDishViewModel.update(favouriteDishDetails)
                                Toast.makeText(this@AddUpdateDishActivity,
                                "You successfully updated your favourite dish details.",
                                Toast.LENGTH_SHORT).show()
                                Log.e("Updating", "Success")
                            }
                              // Переносим этот код выше
//                            // 20.4) Вставляем во viewModel детали
//                            mFavouriteDishViewModel.insert(favouriteDishDetails)
//                            // 20.5) Пишем другое тоаст сообщение
//                            Toast.makeText(this@AddUpdateDishActivity,
//                                "You successfully added your favourite dish details",
//                                Toast.LENGTH_SHORT).show()
//                            Log.i("Insertion", "Success")
                            finish()
                        }

                    }
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
                        // 10.2) Удаляем тоаст сообщение и создаем интент
//                        Toast.makeText(this@AddUpdateDishActivity,
//                            "You have the Gallery permission now to select image",
//                            Toast.LENGTH_SHORT).show()
                        val galleryIntent = Intent(Intent.ACTION_PICK,
                            MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                        startActivityForResult(galleryIntent, GALLERY)
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

    // 13.13) Создаем метод для окна пользовательских элементов
    private fun customItemsListDialog(title: String, itemList: List<String>, selection: String) {
        mCustomListDialog = Dialog(this) // 14.2) Заменяем customListDialog на mCustomListDialog
        val binding: DialogCustomListBinding = DialogCustomListBinding.inflate(layoutInflater)

        mCustomListDialog.setContentView(binding.root) // 14.3) Заменяем customListDialog на mCustomListDialog
        binding.textViewTitle.text = title

        binding.recyclerViewList.layoutManager = LinearLayoutManager(this)

        // 13.14) Создаем адаптер
        val adapter = CustomListItemAdapter(this, itemList, selection)
        binding.recyclerViewList.adapter = adapter
        mCustomListDialog.show() // 14.4) Заменяем customListDialog на mCustomListDialog
    }


    // 14.5) Создаем метод для выбора элемента списка
    fun selectedListItem(item: String, selection: String) {
        when (selection) {
            Constants.DISH_TYPE -> {
                mCustomListDialog.dismiss()
                mBinding.editTextType.setText(item)
            }
            Constants.DISH_CATEGORY -> {
                mCustomListDialog.dismiss()
                mBinding.editTextCategory.setText(item)
            }
            else -> {
                mCustomListDialog.dismiss()
                mBinding.editTextCookingTime.setText(item)
            }
        }
    }

    // 9.5) Создаем метод
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == CAMERA) {
                data?.extras?.let {
                    val thumbnail: Bitmap = data.extras!!.get("data") as Bitmap

                    // 11.2) Удаляем эту строчку кода и воспользуемся Glide
                    // mBinding.imageViewDish.setImageBitmap(thumbnail)
                    Glide.with(this@AddUpdateDishActivity)
                        .load(thumbnail)
                        .centerCrop()
                        .into(mBinding.imageViewDish)

                    // 12.4) Вставляем в путь картинки наш метод
                    mImagePath = saveImageToInternalStorage(thumbnail)
                    Log.i("ImagePath", mImagePath)

                    // 9.6) Загружаем векторную картинку edit

                    // 9.7) Изменяем иконку фотоаппарата с плюсиком на карандаш
                    mBinding.imageViewAddDish.setImageDrawable(ContextCompat.getDrawable(this,
                        R.drawable.ic_vector_edit))
                }
            }
            // 10.3) Добавляем проверку для галереи
            if (requestCode == GALLERY) {
                data?.let {
                    val selectedPhotoUri = data.data

                    // 11.3) Удаляем эту строчку кода и воспользуемся Glide
                    //mBinding.imageViewDish.setImageURI(selectedPhotoUri)
                    Glide.with(this@AddUpdateDishActivity)
                        .load(selectedPhotoUri)
                        .centerCrop()
                        // 12.5) Добавляем
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        // 12.6) Добавляем два метода
                        .listener(object : RequestListener<Drawable> {
                            override fun onLoadFailed(
                                e: GlideException?,
                                model: Any?,
                                target: Target<Drawable>?,
                                isFirstResource: Boolean,
                            ): Boolean {
                                // 12.7) Добавляем Log
                                Log.e("TAG", "Error loading image", e)
                                return false
                            }

                            override fun onResourceReady(
                                resource: Drawable?,
                                model: Any?,
                                target: Target<Drawable>?,
                                dataSource: DataSource?,
                                isFirstResource: Boolean,
                            ): Boolean {
                                // 12.8)
                                resource?.let {
                                    val bitmap: Bitmap = resource.toBitmap()
                                    mImagePath = saveImageToInternalStorage(bitmap)
                                    Log.i("ImagePath", mImagePath)
                                }
                                return false

                                // 13.1) Создаем папку utils
                            }

                        })
                        .into(mBinding.imageViewDish)

                    mBinding.imageViewAddDish.setImageDrawable(ContextCompat.getDrawable(this,
                        R.drawable.ic_vector_edit))
                }
            }
            // 10.4) Добавляем
        } else if (resultCode == Activity.RESULT_CANCELED) {
            Log.e("cancelled", "User cancelled image selection")
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

    // 12.2) Создаем метод для сохранения изображения во внутреннюю память
    private fun saveImageToInternalStorage(bitmap: Bitmap): String {
        val wrapper = ContextWrapper(applicationContext)

        var file = wrapper.getDir(IMAGE_DIRECTORY, Context.MODE_PRIVATE)
        file = File(file, "${UUID.randomUUID()}.jpg")

        try {
            val stream: OutputStream = FileOutputStream(file)
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream)
            stream.flush()
            stream.close()
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return file.absolutePath
    }


    // 9.4) Создаем константу для камеры
    companion object {

        private const val CAMERA = 1

        // 10.1) Создаем константу для галереи
        private const val GALLERY = 2

        // 12.1) Создаем константу для директории картинки
        private const val IMAGE_DIRECTORY = "FavouriteDishImages"
    }
}

// 11) В манифесте добавляем label И theme
// 12) Меняем название HomeFragment на AllDishesFragment и xml файл
// 13) Добавляем иконку плюс
// 14) Далее создаем в папку menu, menu_all_dishes

// 8.1) Добавляем разрешение в манифест
// 8.2) Добавляем библиотеку dexter в gradle
// 11.1) Добавляем библиотеку Glide в gradle