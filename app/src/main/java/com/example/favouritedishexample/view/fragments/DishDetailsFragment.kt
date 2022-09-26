package com.example.favouritedishexample.view.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.favouritedishexample.R
import com.example.favouritedishexample.databinding.FragmentDishDetailsBinding
import java.io.IOException
import java.util.*


// 24.1) Создаем новый фрагмент
class DishDetailsFragment : Fragment() {

    // 27.2) Создаем binding
    private var mBinding: FragmentDishDetailsBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {

        // 27.3)
        mBinding = FragmentDishDetailsBinding.inflate(inflater,container,false)
        return mBinding!!.root
    }

    // 26.7) Добавляем метод и добавляем args и логи
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val args: DishDetailsFragmentArgs by navArgs()
        Log.i("Dish Title", args.dishDetails.title)
        Log.i("Dish Type", args.dishDetails.type)

        // 27.4)
        args.let {
            try {
                Glide.with(requireActivity())
                    .load(it.dishDetails.image)
                    .centerCrop()
                    .into(mBinding!!.imageViewDishImage)
            }catch (e:IOException){
                e.printStackTrace()
            }
            // 27.5) Выводим в текст компоненты
            mBinding!!.textViewTitle.text = it.dishDetails.title
            mBinding!!.textViewType.text = it.dishDetails.type.capitalize(Locale.ROOT)
            mBinding!!.textViewCategory.text = it.dishDetails.category
            mBinding!!.textViewIngredients.text = it.dishDetails.ingredients
            mBinding!!.textViewCookingDirection.text = it.dishDetails.directionToCook
            mBinding!!.textViewCookingTime.text =
                resources.getString(R.string.label_estimate_cooking_time, it.dishDetails.cookingTime)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        mBinding = null
    }
}
// 24.2) Добавляем плагин safeargs в gradle(Module)
// Также добавляем версию плагина safeargs в gradle(Project)

// 27.1) Меняем компоненты во fragment_dish_details