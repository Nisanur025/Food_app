package com.example.food_app

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.food_app.databinding.RvItemRecipeBinding

// Tıklama Olayları
    class AllRecipeAdapter(
        private val onRecipeClick: (AllRecipe) -> Unit,
        private val onFavoriteClick: (AllRecipe) -> Unit
    ) : ListAdapter<AllRecipe, AllRecipeAdapter.RecipeViewHolder>(RecipeDiffCallback()) {


        // Ekran ilk yüklendiğinde
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeViewHolder {
        val binding = RvItemRecipeBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return RecipeViewHolder(binding)
    }

    // Kullanıcı kaydırdıkça verileri doldur
    override fun onBindViewHolder(holder: RecipeViewHolder, position: Int) {
        holder.bind(getItem(position))
    }


    // kartşarın tıklama olaylarını kontrol edre
    inner class RecipeViewHolder(
        private val binding: RvItemRecipeBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(recipe: AllRecipe) {
            // Metin değerlerini atama
            binding.textTitle.text = recipe.title
            binding.textSubInfo.text = recipe.difficulty
            binding.textDuration.text = recipe.durationLabel

            // Görsel yükleme
            binding.imageRecipe.load(recipe.imageUrl) {
                crossfade(false)
                placeholder(R.drawable.bg_image_placeholder)
            }

            // Favori durum güncelleme
            updateFavoriteIcon(recipe.isFavorite)

            // favori buton tıklama
            binding.iconFavorite.setOnClickListener {
                recipe.isFavorite = !recipe.isFavorite
                updateFavoriteIcon(recipe.isFavorite)
                onFavoriteClick(recipe)
            }

            // Kartın tamamına tıklama
            binding.root.setOnClickListener { onRecipeClick(recipe) }
        }

        // favori fonksiyonu
        private fun updateFavoriteIcon(isFavorite: Boolean) {
            val iconRes = if (isFavorite) R.drawable.ic_heart_filled else R.drawable.ic_heart
            binding.iconFavorite.setImageResource(iconRes)
        }
    }
}

class RecipeDiffCallback : DiffUtil.ItemCallback<AllRecipe>() {
    override fun areItemsTheSame(oldItem: AllRecipe, newItem: AllRecipe): Boolean =
        oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: AllRecipe, newItem: AllRecipe): Boolean =
        oldItem == newItem
}