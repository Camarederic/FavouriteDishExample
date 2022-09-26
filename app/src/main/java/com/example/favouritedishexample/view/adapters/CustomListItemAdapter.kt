package com.example.favouritedishexample.view.adapters

import android.app.Activity
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.favouritedishexample.databinding.ItemCustomListBinding
import com.example.favouritedishexample.view.activities.AddUpdateDishActivity
import com.example.favouritedishexample.view.fragments.AllDishesFragment

// 13.10) Создаем адаптер с тремя параметрами
class CustomListItemAdapter(
    private val activity: Activity,
    private val fragment: Fragment?, // 37.11) Добавляем эту строчку
    private val listItems: List<String>,
    private val selection: String,
) : RecyclerView.Adapter<CustomListItemAdapter.ViewHolder>() {

    // 13.11) Создаем класс ViewHolder
    class ViewHolder(view: ItemCustomListBinding):RecyclerView.ViewHolder(view.root) {
        val tvText = view.textViewText
    }

    // 13.12) Имплементируем три метода
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: ItemCustomListBinding = ItemCustomListBinding
            .inflate(LayoutInflater.from(activity), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = listItems[position]
        holder.tvText.text = item

        // 14.6) Создаем клик слушателя для itemView
        holder.itemView.setOnClickListener {
            if (activity is AddUpdateDishActivity){
                activity.selectedListItem(item, selection)
            }
            // 37.14) Добавляем
            if (fragment is AllDishesFragment){
                fragment.filterSelection(item)
            }
        }
    }

    override fun getItemCount(): Int {
        return listItems.size
    }

}