package com.example.food_app

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.widget.TextViewCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.food_app.databinding.ItemDayMenuBinding

class DayMenuAdapter(
    private val onClick: (day_menu) -> Unit
) : ListAdapter<day_menu, DayMenuAdapter.VH>(Diff) {

    inner class VH(private val b: ItemDayMenuBinding) : RecyclerView.ViewHolder(b.root) {
        fun bind(item: day_menu) = with(b) {
            tvDayName.setText(item.dayNameRes)

            if (item.recipeName != null) {
                ivStatus.setImageResource(R.drawable.ic_check)
                tvRecipe.text = item.recipeName
                TextViewCompat.setTextAppearance(tvRecipe, R.style.TextAppearance_App_DayRecipe)
            } else {
                ivStatus.setImageResource(R.drawable.ic_plus)
                tvRecipe.setText(R.string.status_not_planned)
                TextViewCompat.setTextAppearance(tvRecipe, R.style.TextAppearance_App_DayEmpty)
            }

            if (item.isToday) {
                root.setCardBackgroundColor(Color.TRANSPARENT)
                dayContent.setBackgroundResource(R.drawable.bg_today_menu)
            } else {
                root.setCardBackgroundColor(ContextCompat.getColor(root.context, R.color.card_bg))
                dayContent.background = null
            }

            root.setOnClickListener { onClick(item) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        VH(ItemDayMenuBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: VH, position: Int) = holder.bind(getItem(position))

    private object Diff : DiffUtil.ItemCallback<day_menu>() {
        override fun areItemsTheSame(a: day_menu, b: day_menu) = a.dayNameRes == b.dayNameRes
        override fun areContentsTheSame(a: day_menu, b: day_menu) = a == b
    }
}