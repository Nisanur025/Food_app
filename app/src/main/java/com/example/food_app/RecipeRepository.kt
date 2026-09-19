package com.example.food_app

object RecipeRepository {
    private val recipeDetails = listOf(
        RecipeDetail(
            id = "1",
            title = "Mercimek Çorbası",
            description = "Kırmızı mercimekle yapılan, kremamsı ve doyurucu klasik türk çorbası",
            photos = listOf(
                "https://picsum.photos/seed/1a/400/300",
                "https://picsum.photos/seed/1b/400/300"
            ),
            defaultServings = 4,
            ingredients = listOf(
                Ingredient("1 cup", "kırmızı mercimek"),
                Ingredient("1 adet", "soğan"),
                Ingredient("2 yemek kaşığı", "tereyağı"),
                Ingredient("1 litre", "su")
            ),
            steps = listOf(
                RecipeStep(1, "Soğanı kavur", "Tereyağını eritip soğanı pembeleşene kadar kavurun."),
                RecipeStep(2, "Mercimeği ekle", "Yıkanmış mercimeği ekleyip birkaç dakika karıştırın."),
                RecipeStep(3, "Suyu ekle ve pişir", "Suyu ilave edip mercimekler yumuşayana kadar pişirin.")
            )
        )
        // Diğer tarifler (id=2,3,4...) buraya aynı şekilde eklenecek
    )

    fun getById(id: String): RecipeDetail? = recipeDetails.find { it.id == id }
}