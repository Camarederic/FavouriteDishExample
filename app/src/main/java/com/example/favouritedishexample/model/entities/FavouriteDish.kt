package com.example.favouritedishexample.model.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

// 15.1) Создаем класс и помечаем его как Entity
@Entity(tableName = "favourite_dishes_table")
data class FavouriteDish(
    @ColumnInfo val image: String,
    @ColumnInfo(name = "image_source") val imageSource: String,
    @ColumnInfo val title: String,
    @ColumnInfo val type: String,
    @ColumnInfo val category: String,
    @ColumnInfo val ingredients: String,

    @ColumnInfo(name = "cooking_time") val cookingTime: String,
    @ColumnInfo(name = "instructions") val directionToCook: String,
    @ColumnInfo(name = "favourite_dish") var favouriteDish: Boolean = false,
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
)