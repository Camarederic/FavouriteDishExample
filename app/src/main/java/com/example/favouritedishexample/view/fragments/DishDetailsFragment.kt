package com.example.favouritedishexample.view.fragments

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.toBitmap
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import androidx.palette.graphics.Palette
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.example.favouritedishexample.R
import com.example.favouritedishexample.application.FavouriteDishApplication
import com.example.favouritedishexample.databinding.FragmentDishDetailsBinding
import com.example.favouritedishexample.viewmodel.FavouriteDishViewModel
import com.example.favouritedishexample.viewmodel.FavouriteDishViewModelFactory
import java.io.IOException
import java.util.*


// 24.1) Создаем новый фрагмент
class DishDetailsFragment : Fragment() {

    // 27.2) Создаем binding
    private var mBinding: FragmentDishDetailsBinding? = null

    // 29.5) Создаем viewModel
    private val mFavouriteDishViewModel: FavouriteDishViewModel by viewModels {
        FavouriteDishViewModelFactory((requireActivity().application as FavouriteDishApplication).repository)
    }

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
                    // 28.2) Добавляем listener и имплементируем два метода
                    .listener(object: RequestListener<Drawable>{
                        override fun onLoadFailed(
                            e: GlideException?,
                            model: Any?,
                            target: Target<Drawable>?,
                            isFirstResource: Boolean,
                        ): Boolean {
                            Log.e("TAG", "ERROR loading Image", e)
                            return false
                        }

                        override fun onResourceReady(
                            resource: Drawable?,
                            model: Any?,
                            target: Target<Drawable>?,
                            dataSource: DataSource?,
                            isFirstResource: Boolean,
                        ): Boolean {
                            // 28.3) Добавляем Palette
                            resource.let {
                                Palette.from(resource!!.toBitmap()).generate(){
                                        palette ->
                                    val intColor = palette?.vibrantSwatch?.rgb ?: 0
                                    mBinding!!.rlDishDetailMain.setBackgroundColor(intColor)
                                }
                            }
                            return false

                        }

                    })
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

            // 29.9)
            if (args.dishDetails.favouriteDish){
                mBinding!!.imageViewFavouriteDish.setImageDrawable(ContextCompat.getDrawable(
                    requireActivity(),
                    R.drawable.ic_favourite_selected))
            }else{
                mBinding!!.imageViewFavouriteDish.setImageDrawable(ContextCompat.getDrawable(
                    requireActivity(),
                    R.drawable.ic_favourite_unselected))
            }

        }

        // 29.4) Создаем клик слушателя для картинки сердца
        mBinding!!.imageViewFavouriteDish.setOnClickListener {
            // 29.6)
            args.dishDetails.favouriteDish = !args.dishDetails.favouriteDish
            // 29.7) Обновляем
            mFavouriteDishViewModel.update(args.dishDetails)

            // 29.8) Меняем сердечки картинки местами и устанавливаем сообщения
            if (args.dishDetails.favouriteDish){
                mBinding!!.imageViewFavouriteDish.setImageDrawable(ContextCompat.getDrawable(
                requireActivity(),
                R.drawable.ic_favourite_selected))

                Toast.makeText(requireActivity(), resources.getString(
                    R.string.message_added_to_favourites), Toast.LENGTH_SHORT).show()
            }else{
                mBinding!!.imageViewFavouriteDish.setImageDrawable(ContextCompat.getDrawable(
                    requireActivity(),
                    R.drawable.ic_favourite_unselected))

                Toast.makeText(requireActivity(), resources.getString(
                    R.string.message_removed_from_favourites),Toast.LENGTH_SHORT).show()
            }
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
// 28.1) Загружаем библиотеку Palette в gradle