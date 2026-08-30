package com.example.food_app

data class AllRecipe(val id: String,
                     val title: String,
                     val imageUrl: String,
                     val durationMinutes: Int,
                     val difficulty: String,      // "Kolay", "Orta", "Zor"
                     val category: String,        // "Kahvaltı", "Ana yemek", "Tatlı" vb.
                     val isVegan: Boolean = false,
                     val ingredientCount: Int = 0,
                     var isFavorite: Boolean = false
) {
    val durationLabel: String
        get() = "$durationMinutes dk"
}
