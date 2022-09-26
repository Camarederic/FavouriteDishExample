package com.example.favouritedishexample.view.fragments

import android.content.Intent
import android.os.Bundle
import android.view.*
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.favouritedishexample.R
import com.example.favouritedishexample.databinding.FragmentAllDishesBinding
import com.example.favouritedishexample.view.activities.AddUpdateDishActivity
import com.example.favouritedishexample.viewmodel.HomeViewModel

class AllDishesFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel
    private var _binding: FragmentAllDishesBinding? = null


    private val binding get() = _binding!!

    // 15) Имплементируем метод onCreate
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // 16) Для появления меню опций
        setHasOptionsMenu(true)

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)

        _binding = FragmentAllDishesBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.textHome
        homeViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })
        return root
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
        when(item.itemId){
            R.id.action_add_dish ->{
                startActivity(Intent(requireActivity(), AddUpdateDishActivity::class.java))
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}