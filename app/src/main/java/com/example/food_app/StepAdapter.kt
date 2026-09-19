
package com.example.food_app

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.food_app.databinding.StepItemBinding

class StepAdapter(private val items: List<RecipeStep>) :
    RecyclerView.Adapter<StepAdapter.VH>() {

    inner class VH(val binding: StepItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val binding = StepItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return VH(binding)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        val item = items[position]
        holder.binding.tvStepNumber.text = item.stepNumber.toString()
        holder.binding.tvStepTitle.text = item.title
        holder.binding.tvStepBody.text = item.body
    }

    override fun getItemCount() = items.size
}