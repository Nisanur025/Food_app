
package com.example.food_app

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.food_app.databinding.IngredientItemBinding

class IngredientAdapter(private val items: List<Ingredient>) :
    RecyclerView.Adapter<IngredientAdapter.VH>() {

    inner class VH(val binding: IngredientItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val binding = IngredientItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return VH(binding)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        val item = items[position]
        holder.binding.tvAmount.text = item.amount
        holder.binding.tvName.text = item.name
    }

    override fun getItemCount() = items.size
}