package com.example.food_app

data class menuItem(
    val ad: String,
    val kategori: String,
    val sure: String,
    val tip: MenuTipi,
)

enum class MenuTipi {
    CORBA, ANA_YEMEK, TATLI, SALATA
}
