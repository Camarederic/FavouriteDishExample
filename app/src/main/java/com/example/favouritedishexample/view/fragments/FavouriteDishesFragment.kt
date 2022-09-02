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
import com.example.favouritedishexample.R
import com.example.favouritedishexample.application.FavouriteDishApplication
import com.example.favouritedishexample.databinding.FragmentFavouriteDishesBinding
import com.example.favouritedishexample.viewmodel.DashboardViewModel
import com.example.favouritedishexample.viewmodel.FavouriteDishViewModel
import com.example.favouritedishexample.viewmodel.FavouriteDishViewModelFactory

class FavouriteDishesFragment : Fragment() {

    private lateinit var dashboardViewModel: DashboardViewModel

    // 30.4) Создаем viewModel
    private val mFavouriteDishesViewModel: FavouriteDishViewModel by viewModels {
        FavouriteDishViewModelFactory((requireActivity().application as FavouriteDishApplication).repository)
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        dashboardViewModel =
            ViewModelProvider(this).get(DashboardViewModel::class.java)

        val root = inflater.inflate(R.layout.fragment_favourite_dishes, container, false)

        val textView: TextView = root.findViewById(R.id.textFavouriteDishes)
        dashboardViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })
        return root
    }

    // 30.5) Создаем метод onViewCreated
    @SuppressLint("LongLogTag")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // 30.6)
        mFavouriteDishesViewModel.favouriteDishes.observe(viewLifecycleOwner) { dishes ->
            dishes.let {
                if (it.isNotEmpty()) {
                    for (dish in it) {
                        Log.i("Favourite Dish", "${dish.id}::${dish.title}")
                    }
                }else{
                    Log.i("List of Favourite Dishes", "is empty.")
                }
            }
        }
    }
}