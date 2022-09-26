package com.example.favouritedishexample.view.fragments

import android.app.Dialog
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.provider.SyncStateContract
import android.text.Html
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.favouritedishexample.R
import com.example.favouritedishexample.application.FavouriteDishApplication
import com.example.favouritedishexample.databinding.FragmentRandomDishBinding
import com.example.favouritedishexample.model.entities.FavouriteDish
import com.example.favouritedishexample.model.entities.RandomDish
import com.example.favouritedishexample.utils.Constants
import com.example.favouritedishexample.viewmodel.FavouriteDishViewModel
import com.example.favouritedishexample.viewmodel.FavouriteDishViewModelFactory
import com.example.favouritedishexample.viewmodel.NotificationsViewModel
import com.example.favouritedishexample.viewmodel.RandomDishViewModel

class RandomDishFragment : Fragment() {

    // 39.4) Создаем binding
    private var mBinding: FragmentRandomDishBinding? = null

    // 44.1) Создаем viewModel
    private lateinit var mRandomDishViewModel: RandomDishViewModel

    // 47.2) Создаем новую переменную
    private var mProgressDialog: Dialog? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {

        // 39.5) Инициализируем binding
        mBinding = FragmentRandomDishBinding.inflate(inflater, container, false)
        return mBinding!!.root
    }
    // 47.3) Создаем функцию для показа пользовательского диалогового окна прогресса
    private fun showCustomProgressDialog(){
        mProgressDialog = Dialog(requireActivity())
        mProgressDialog?.let {
            it.setContentView(R.layout.dialog_custom_progress)
            it.show()
        }
    }
    // 47.4) Создаем функцию для скрытия диалогового окна
    private fun hideProgressDialog(){
        mProgressDialog?.let {
            it.dismiss()
        }
    }

    // 44.2) Создаем метод onViewCreated
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mRandomDishViewModel = ViewModelProvider(this).get(RandomDishViewModel::class.java)
        // 44.4)
        mRandomDishViewModel.getRandomRecipeFromAPI()

        // 44.9) Вызываем метод
        randomDishViewModelObserver()

        // 46.3) Устнавливаем обновление слушателя
        mBinding!!.srlRandomDish.setOnRefreshListener {
            mRandomDishViewModel.getRandomRecipeFromAPI()
        }

    }

    // 44.5) Создаем функцию
    private fun randomDishViewModelObserver() {
        // 44.6) Создаем observe для randomDishResponse
        mRandomDishViewModel.randomDishResponse.observe(viewLifecycleOwner,
            { randomDishResponse ->
                randomDishResponse?.let {
                    Log.i("Random Dish Response", "$randomDishResponse.recipes[0]")

                    // 46.9) Исчезновение обновления
                    if (mBinding!!.srlRandomDish.isRefreshing){
                        mBinding!!.srlRandomDish.isRefreshing = false
                    }

                    // 45.6) Вызываем метод
                    setRandomDishResponseInUI(randomDishResponse.recipes[0])
                    // 45.7) Далее идем в activity_main.xml и добавляем
                    // 45.8) Далее идем в MainActivity
                }
            }
        )
        // 44.7) Создаем observe для randomDishLoadingError
        mRandomDishViewModel.randomDishLoadingError.observe(viewLifecycleOwner,
            { dataError ->
                dataError?.let {
                    Log.e("Random Dish API Error", "$dataError")

                    // 46.10) Исчезновение обновления
                    if (mBinding!!.srlRandomDish.isRefreshing){
                        mBinding!!.srlRandomDish.isRefreshing = false
                    }
                }
            }
        )
        // 44.8) Создаем observe для loadRandomDish
        mRandomDishViewModel.loadRandomDish.observe(viewLifecycleOwner,
            { loadRandomDish ->
                loadRandomDish?.let {
                    Log.i("Random Dish Loading", "$loadRandomDish")

                    // 47.5) Проверяем
                    if (loadRandomDish && !mBinding!!.srlRandomDish.isRefreshing){
                        showCustomProgressDialog()
                    }else{
                        hideProgressDialog()
                    }
                }
            }
        )
    }

    // 45.1) Создаем функцию для установки случайного отклика блюда
    private fun setRandomDishResponseInUI(recipe: RandomDish.Recipe) {
        // 45.2) Используем Glide
        Glide.with(requireActivity())
            .load(recipe.image)
            .centerCrop()
            .into(mBinding!!.imageViewDishImage)

        mBinding!!.textViewTitle.text = recipe.title

        // 45.3)
        var dishType: String = "other"
        if (recipe.dishTypes.isNotEmpty()) {
            dishType = recipe.dishTypes[0]
            mBinding!!.textViewType.text = dishType
        }
        // 45.4)
        mBinding!!.textViewCategory.text = "Other"
        var ingredients = ""
        for (value in recipe.extendedIngredients) {
            if (ingredients.isEmpty()) {
                ingredients = value.original
            } else {
                ingredients = ingredients + ", \n" + value.original
            }
        }
        mBinding!!.textViewIngredients.text = ingredients

        // 45.5)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            mBinding!!.textViewCookingDirection.text = Html.fromHtml(
                recipe.instructions,
                Html.FROM_HTML_MODE_COMPACT)
        } else {
            @Suppress("DEPRECATION")
            mBinding!!.textViewCookingDirection.text = Html.fromHtml(recipe.instructions)
        }

        // 46.4) Устнавливаем unselected сердечко
        mBinding!!.imageViewFavouriteDish.setImageDrawable(
            ContextCompat.getDrawable(requireActivity(),
                R.drawable.ic_favourite_unselected)
        )

        // 46.5)
        var addedToFavourites = false

        mBinding!!.textViewCookingTime.text =
            resources.getString(
                R.string.label_estimate_cooking_time,
                recipe.readyInMinutes.toString()
            )

        // 45.11) Устнавливаем клик слушателя на сердечко
        mBinding!!.imageViewFavouriteDish.setOnClickListener {

            // 46.7) Проверяем
            if (addedToFavourites) {
                Toast.makeText(requireActivity(),
                    resources.getString(R.string.message_already_added_to_favourites),
                    Toast.LENGTH_SHORT).show()
                // 46.8) Переносим код в else
            } else {
                val randomDishDetails = FavouriteDish(
                    recipe.image,
                    Constants.DISH_IMAGE_SOURCE_ONLINE,
                    recipe.title,
                    dishType,
                    "Other",
                    ingredients,
                    recipe.readyInMinutes.toString(),
                    recipe.instructions,
                    true
                )
                // 45.12) Создаем viewModel
                val mFavouriteDishViewModel: FavouriteDishViewModel by viewModels {
                    FavouriteDishViewModelFactory((requireActivity().application as FavouriteDishApplication).repository)
                }
                mFavouriteDishViewModel.insert(randomDishDetails)

                // 46.6)
                addedToFavourites = true

                // 45.13) Устнавливаем красное сердечко
                mBinding!!.imageViewFavouriteDish.setImageDrawable(
                    ContextCompat.getDrawable(
                        requireActivity(),
                        R.drawable.ic_favourite_selected
                    )
                )
                Toast.makeText(requireActivity(),
                    resources.getString(R.string.message_added_to_favourites),
                    Toast.LENGTH_SHORT).show()
            }
        }


        // 46.1) Идем в fragment_random_dish и добавляем
        // 46.2) Идем в string и добавляем новый message

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
// 47.1) Создаем новый layout dialog_custom_progress