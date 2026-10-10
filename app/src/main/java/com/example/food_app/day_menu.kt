package com.example.food_app

import androidx.annotation.StringRes

data class day_menu(
    @StringRes val dayNameRes: Int,
    val recipeName: String?, // null olabilir planlanmadı
    val isToday: Boolean = false

)
