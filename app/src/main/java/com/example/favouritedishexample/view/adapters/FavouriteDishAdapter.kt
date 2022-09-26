package com.example.favouritedishexample.view.adapters

import android.annotation.SuppressLint
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.favouritedishexample.R
import com.example.favouritedishexample.databinding.ItemDishLayoutBinding
import com.example.favouritedishexample.model.entities.FavouriteDish
import com.example.favouritedishexample.utils.Constants
import com.example.favouritedishexample.view.activities.AddUpdateDishActivity
import com.example.favouritedishexample.view.fragments.AllDishesFragment
import com.example.favouritedishexample.view.fragments.FavouriteDishesFragment

// 22.3) Создаем адаптер
class FavouriteDishAdapter(private val fragment: Fragment) :
    RecyclerView.Adapter<FavouriteDishAdapter.ViewHolder>() {

    // 22.6) Создаем список
    private var dishes: List<FavouriteDish> = listOf()

    // 22.4) Создаем класс ViewHolder
    class ViewHolder(view: ItemDishLayoutBinding) : RecyclerView.ViewHolder(view.root) {
        val imageViewDishImage = view.imageViewDishImage
        val textViewDishTitle = view.textViewDishTitle

        // 33.4) Добавляем для imageButton
        val imageButtonMore = view.imageButtonMore
    }

    // 22.5) Имплементируем три метода
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // 22.9)
        val binding: ItemDishLayoutBinding = ItemDishLayoutBinding
            .inflate(LayoutInflater.from(fragment.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        // 22.8)
        val dish = dishes[position]
        Glide.with(fragment)
            .load(dish.image)
            .into(holder.imageViewDishImage)
        holder.textViewDishTitle.text = dish.title

        // 24.5) Устанавливаем чтобы при клике на item, входили в item
        holder.itemView.setOnClickListener {
            if (fragment is AllDishesFragment) {
                fragment.dishDetails(dish) // 26.6) Добавляем dish
            }
            // 32.4) Добавляем проверку
            if (fragment is FavouriteDishesFragment) {
                fragment.dishDetails(dish)
            }
        }

        // 33.5) Добавляем клик слушателя для imageButtonMore
        holder.imageButtonMore.setOnClickListener {
            // 33.6) Создаем объект PopupMenu
            val popup = PopupMenu(fragment.context, holder.imageButtonMore)
            popup.menuInflater.inflate(R.menu.menu_adapter, popup.menu)

            // 33.7) Создаем клик слушателя для EDIT DISH и DELETE DISH
            popup.setOnMenuItemClickListener {
                if (it.itemId == R.id.action_edit_dish) {
                    Log.i("You have clicked on", "Edit Option of ${dish.title}")
                    // 34.2) Создаем интент для перехода из фрагмента в активность обновления еды
                    val intent =
                        Intent(fragment.requireActivity(), AddUpdateDishActivity::class.java)
                    intent.putExtra(Constants.EXTRA_DISH_DETAILS, dish)
                    fragment.requireActivity().startActivity(intent)

                } else if (it.itemId == R.id.action_delete_dish) {
                    Log.i("You have clicked on", "Delete Option of ${dish.title}")
                    // 35.11)
                    if (fragment is AllDishesFragment){
                        fragment.deleteDish(dish)
                    }
                }
                true
            }
            popup.show()
        }
        // 33.8) Устанавливаем видимость и исчезновение popupMenu для разных фрагментов
        if (fragment is AllDishesFragment) {
            holder.imageButtonMore.visibility = View.VISIBLE
        } else if (fragment is FavouriteDishesFragment) {
            holder.imageButtonMore.visibility = View.GONE
        }
    }

    override fun getItemCount(): Int {
        return dishes.size
    }

    // 22.7) Создаем метод для списка еды
    fun dishesList(list: List<FavouriteDish>) {
        dishes = list
        notifyDataSetChanged()
    }
}