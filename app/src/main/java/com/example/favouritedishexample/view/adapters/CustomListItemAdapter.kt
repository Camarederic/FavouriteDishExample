package com.example.favouritedishexample.view.adapters

import android.app.Activity
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.favouritedishexample.databinding.ItemCustomListBinding
import com.example.favouritedishexample.view.activities.AddUpdateDishActivity

// 13.10) Создаем адаптер с тремя параметрами
class CustomListItemAdapter(
    private val activity: Activity,
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
        }
    }

    override fun getItemCount(): Int {
        return listItems.size
    }

}