package com.example.favouritedishexample.view.fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.GridLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.favouritedishexample.R
import com.example.favouritedishexample.application.FavouriteDishApplication
import com.example.favouritedishexample.databinding.FragmentAllDishesBinding
import com.example.favouritedishexample.model.entities.FavouriteDish
import com.example.favouritedishexample.view.activities.AddUpdateDishActivity
import com.example.favouritedishexample.view.activities.MainActivity
import com.example.favouritedishexample.view.adapters.FavouriteDishAdapter
import com.example.favouritedishexample.viewmodel.FavouriteDishViewModel
import com.example.favouritedishexample.viewmodel.FavouriteDishViewModelFactory
import com.example.favouritedishexample.viewmodel.HomeViewModel

class AllDishesFragment : Fragment() {


    private lateinit var mBinding: FragmentAllDishesBinding

    // 21.4) Создаем viewModel
    private val mFavouriteDishViewModel: FavouriteDishViewModel by viewModels {
        FavouriteDishViewModelFactory((requireActivity().application as FavouriteDishApplication).repository)
    }


    // 15) Имплементируем метод onCreate
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // 16) Для появления меню опций
        setHasOptionsMenu(true)

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // 22.10) Удаляем этот код и пишем другой
//        homeViewModel =
//            ViewModelProvider(this).get(HomeViewModel::class.java)
//
//        _binding = FragmentAllDishesBinding.inflate(inflater, container, false)
//        val root: View = binding.root
//
//        val textView: TextView = binding.textViewNoDishesAddedYet
//        homeViewModel.text.observe(viewLifecycleOwner, Observer {
//            textView.text = it
//        })
        mBinding = FragmentAllDishesBinding.inflate(inflater, container,false)
        return mBinding.root
    }

    // 21.5) Создаем метод onViewCreated
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // 22.11) Устанавливаем расположение как сетка
        mBinding.recyclerViewDishesList.layoutManager = GridLayoutManager(requireActivity(),2)
        val favouriteDishAdapter = FavouriteDishAdapter(this@AllDishesFragment)
        mBinding.recyclerViewDishesList.adapter = favouriteDishAdapter


        // 21.6)
        mFavouriteDishViewModel.allDishesList.observe(viewLifecycleOwner){
            dishes ->
            dishes.let {
                // 22.12) Проверяем на пустоту
                if (it.isNotEmpty()){
                    // 22.13) Устанавливаем видимость
                    mBinding.recyclerViewDishesList.visibility = View.VISIBLE
                    mBinding.textViewNoDishesAddedYet.visibility = View.GONE
                    favouriteDishAdapter.dishesList(it)
                }else{
                    mBinding.recyclerViewDishesList.visibility = View.GONE
                    mBinding.textViewNoDishesAddedYet.visibility = View.VISIBLE


                }
                for (item in it){
                    Log.i("Dish Title", "${item.id}::${item.title}")
                }
            }
        }
    }

    // 22.1) Идем во fragment_all_dishes и изменяем
    // 22.2) Создаем item_dish_layout
    // 22.3) Создаем адаптер FavouriteDishAdapter
    // 23.1) Переименовываем фрагменты
    // 26.1) Вставляем в gradle новый плагин
    // 26.3) Далее идем в mobile_navigation.xml и во фрагменте navigation_dish_details добавляем аргумент

    // 24.3) Создаем метод для деталей еды
    fun dishDetails(favouriteDish: FavouriteDish){ // 26.5) Добавляем аргумент
        // 24.4) Устанавливаем навигацию из фрагмента AllDishes во фрагмент DishDetails
        findNavController().navigate(AllDishesFragmentDirections.actionAllDishesToDishDetails(
            favouriteDish)) // 26.4) Добавляем

        // 25.6) Делаем проверку и прячем BottomNavigation
        if (requireActivity() is MainActivity){
            (activity as MainActivity?)?.hideBottomNavigationView()
        }
    }

    // 25.7) Создаем метод onResume и в нем вызываем метод для появления BottomNavigation
    override fun onResume() {
        super.onResume()
        if (requireActivity() is MainActivity){
            (activity as MainActivity?)?.showBottomNavigationView()
        }
    }

    // 17) Имплементируем метод для создания меню
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        // 18)
        inflater.inflate(R.menu.menu_all_dishes, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    // 19) Имплементируем метод для клика меню
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // 20) Когда мы кликаем на плюсик, то переходим AddUpdateDishActivity
        when (item.itemId) {
            R.id.action_add_dish -> {
                startActivity(Intent(requireActivity(), AddUpdateDishActivity::class.java))
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onDestroyView() {
        super.onDestroyView()

    }
}