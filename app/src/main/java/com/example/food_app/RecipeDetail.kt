package com.example.food_app

data class RecipeDetail(
    val id: String,
    val title: String,
    val description: String,
    val photos: List<String>,       // galeri resimleri
    val defaultServings: Int,
    val ingredients: List<Ingredient>,
    val steps: List<RecipeStep>,
    val personalNote: String = ""
)
