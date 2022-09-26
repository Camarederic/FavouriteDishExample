package com.example.favouritedishexample.utils

// 13.2) Создаем объект Константы
object Constants {
    // 13.3) Создаем константы для типа, категории и времени приготовлении еды
    const val DISH_TYPE: String = "DishType"
    const val DISH_CATEGORY: String = "DishCategory"
    const val DISH_COOKING_TIME: String = "DishCookingTime"

    // 20.2) Создаем две константы
    const val DISH_IMAGE_SOURCE_LOCAL: String = "Local"
    const val DISH_IMAGE_SOURCE_ONLINE: String = "Online"

    // 34.1) Создаем константу
    const val EXTRA_DISH_DETAILS: String = "DishDetails"

    // 36.6) Создаем две константы для filter menu item
    const val ALL_ITEMS: String = "All"
    const val FILTER_SELECTION: String = "FilterSelection"

    // 41.1) Создаем константу
    const val API_ENDPOINT: String = "recipes/random"

    // 41.4) Создаем константы для параметров из сайта для рецептов
    const val API_KEY: String = "apiKey"
    const val LIMIT_LICENSE: String = "limitLicense"
    const val TAGS: String = "tags"
    const val NUMBER: String = "number"

    // 42.2) Создаем константу для url
    const val BASE_URL = "https://api.spoonacular.com/"

    // 42.3) Создаем константу своего Api Key(ключ на сайте рецептов)
    const val API_KEY_VALUE: String = "2be8b0061bbb48f9bcba47cba44016e9"

    // 42.4) Создаем константы
    const val LIMIT_LICENSE_VALUE: Boolean = true
    const val TAGS_VALUE: String = "vegetarian, dessert"
    const val NUMBER_VALUE: Int = 1


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
    fun dishCategories(): ArrayList<String> {
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
    fun dishCookTime(): ArrayList<String> {
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