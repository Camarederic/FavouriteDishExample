package com.example.favouritedishexample.view.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.favouritedishexample.databinding.ItemDishLayoutBinding
import com.example.favouritedishexample.model.entities.FavouriteDish
import com.example.favouritedishexample.view.fragments.AllDishesFragment

// 22.3) Создаем адаптер
class FavouriteDishAdapter(private val fragment: Fragment) :
    RecyclerView.Adapter<FavouriteDishAdapter.ViewHolder>() {

    // 22.6) Создаем список
    private var dishes: List<FavouriteDish> = listOf()

    // 22.4) Создаем класс ViewHolder
    class ViewHolder(view: ItemDishLayoutBinding) : RecyclerView.ViewHolder(view.root) {
        val imageViewDishImage = view.imageViewDishImage
        val textViewDishTitle = view.textViewDishTitle
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
        }
    }

    override fun getItemCount(): Int {
        return dishes.size
    }

    // 22.7) Создаем метод для списка еды
    @SuppressLint("NotifyDataSetChanged")
    fun dishesList(list: List<FavouriteDish>) {
        dishes = list
        notifyDataSetChanged()
    }
}