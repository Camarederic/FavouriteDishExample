package com.example.favouritedishexample.utils

// 13.2) Создаем объект Константы
object Constants {
    // 13.3) Создаем константы для типа, категории и времени приготовлении еды
    const val DISH_TYPE: String = "DishType"
    const val DISH_CATEGORY: String = "DishCategory"
    const val DISH_COOKING_TIME: String = "DishCookingTime"

    // 13.4) Создаем метод для типов еды
    fun dishTypes(): ArrayList<String> {
        val list = ArrayList<String>()
        list.add("breakfast")
        list.add("lunch")
        list.add("snacks")
        list.add("dinner")
        list.add("salad")
        list.add("side dish")
        list.add("dessert")
        list.add("other")
        return list
    }

    // 13.5) Создаем метод для категорий еды
    fun dishCategories():ArrayList<String>{
        val list = ArrayList<String>()
        list.add("Pizza")
        list.add("BBQ")
        list.add("Bakery")
        list.add("Burger")
        list.add("Cafe")
        list.add("Chicken")
        list.add("Dessert")
        list.add("Drinks")
        list.add("Hot Dogs")
        list.add("Juices")
        list.add("Sandwich")
        list.add("Tea $ Coffee")
        list.add("Wraps")
        list.add("Other")
        return list
    }

    // 13.6) Создаем метод для приготовления еды
    fun dishCookTime():ArrayList<String>{
        val list = ArrayList<String>()
        list.add("10")
        list.add("15")
        list.add("20")
        list.add("30")
        list.add("45")
        list.add("50")
        list.add("60")
        list.add("90")
        list.add("120")
        list.add("150")
        list.add("180")
        return list
    }
    // 13.7) Создаем в layout dialog_custom_list
    // 13.8) Создаем в layout item_custom_list
    // 13.9) Создаем в папку view папку adapters

}