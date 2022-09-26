package com.example.favouritedishexample.view.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.favouritedishexample.R
import com.example.favouritedishexample.application.FavouriteDishApplication
import com.example.favouritedishexample.databinding.FragmentFavouriteDishesBinding
import com.example.favouritedishexample.model.entities.FavouriteDish
import com.example.favouritedishexample.view.activities.MainActivity
import com.example.favouritedishexample.view.adapters.FavouriteDishAdapter
import com.example.favouritedishexample.viewmodel.DashboardViewModel
import com.example.favouritedishexample.viewmodel.FavouriteDishViewModel
import com.example.favouritedishexample.viewmodel.FavouriteDishViewModelFactory

class FavouriteDishesFragment : Fragment() {


    // 31.2) Добавляем binding
    private var mBinding: FragmentFavouriteDishesBinding? = null

    // 30.4) Создаем viewModel
    private val mFavouriteDishesViewModel: FavouriteDishViewModel by viewModels {
        FavouriteDishViewModelFactory((requireActivity().application as FavouriteDishApplication).repository)
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {

        // 31.4) Инициализируем binding
        mBinding = FragmentFavouriteDishesBinding.inflate(inflater, container, false)

        // 31.5) Удаляем этот код
//        dashboardViewModel =
//            ViewModelProvider(this).get(DashboardViewModel::class.java)
//
//        val root = inflater.inflate(R.layout.fragment_favourite_dishes, container, false)
//
//        val textView: TextView = root.findViewById(R.id.textViewNoFavouriteDishesAvailable)
//        dashboardViewModel.text.observe(viewLifecycleOwner, Observer {
//            textView.text = it
//        })
        return mBinding!!.root
    }

    // 30.5) Создаем метод onViewCreated
    @SuppressLint("LongLogTag")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // 30.6)
        mFavouriteDishesViewModel.favouriteDishes.observe(viewLifecycleOwner) { dishes ->
            dishes.let {

                // 31.6) Устанавливаем сетку для recyclerView и адаптер
                mBinding!!.recyclerViewFavouriteDishesList.layoutManager =
                    GridLayoutManager(requireActivity(),2)
                val adapter = FavouriteDishAdapter(this)
                mBinding!!.recyclerViewFavouriteDishesList.adapter = adapter

                if (it.isNotEmpty()) {
                    // 31.7) Удаляем этот код
//                    for (dish in it) {
//                        Log.i("Favourite Dish", "${dish.id}::${dish.title}")
//                    }
                    // 31.8) Устнавливаем видимость
                    mBinding!!.recyclerViewFavouriteDishesList.visibility = View.VISIBLE
                    mBinding!!.textViewNoFavouriteDishesAvailable.visibility = View.GONE
                    adapter.dishesList(it)
                }else{
                    Log.i("List of Favourite Dishes", "is empty.")
                    mBinding!!.recyclerViewFavouriteDishesList.visibility = View.GONE
                    mBinding!!.textViewNoFavouriteDishesAvailable.visibility = View.VISIBLE
                }
            }
        }
    }

    // 32.2) Создаем метод для деталей еды
    fun dishDetails(favouriteDish: FavouriteDish){
        // 32.5) Добавляем findNavController
        findNavController().navigate(FavouriteDishesFragmentDirections.actionFavouriteDishesToDishDetails(favouriteDish))

        // 32.3) Прячем bottomNavigation
        if (requireActivity() is MainActivity){
            (activity as MainActivity?)!!.hideBottomNavigationView()
        }
    }

    // 31.3) Создаем метод onDestroy
    override fun onDestroy() {
        super.onDestroy()
        mBinding = null
    }

    // 32.6) Создаем метод onResume
    override fun onResume() {
        super.onResume()
        // 32.7) Показываем bottomNavigation
        if (requireActivity() is MainActivity){
            (activity as MainActivity?)!!.showBottomNavigationView()

        }
    }
}
// 31.1) Идем в fragment_favourite_dishes и добавляем recyclerView
// 32.1) Идем в mobile_navigation и добавляем action во fragment
// 33.1) Добавляем новуб иконку ic_more
// 33.2) Идем в item_dish_layout и добавляем иконку ic_more
// 33.3) Создаем в папке menu menu_adapter