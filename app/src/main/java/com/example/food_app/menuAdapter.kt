package com.example.food_app

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView

class MenuAdapter(private val items: List<menuItem>) :
    RecyclerView.Adapter<MenuAdapter.MenuViewHolder>() {

    class MenuViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val iconCircle: FrameLayout = view.findViewById(R.id.iconCircle)
        val icon: ImageView = view.findViewById(R.id.icon)
        val ad: TextView = view.findViewById(R.id.tvAd)
        val kategoriChip: TextView = view.findViewById(R.id.tv_kategory_guest)
        val sureChip: TextView = view.findViewById(R.id.tvTime)
        val onayIcon: ImageView = view.findViewById(R.id.iv_confirm)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MenuViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.rv_item_menu_row, parent, false)
        return MenuViewHolder(view)
    }

    override fun onBindViewHolder(holder: MenuViewHolder, position: Int) {
        val item = items[position]
        val context = holder.itemView.context

        holder.ad.text = item.ad
        holder.kategoriChip.text = item.kategori
        holder.sureChip.text = item.sure
        holder.sureChip.visibility = if (item.sure.isNotEmpty()) View.VISIBLE else View.GONE

        // Tipe göre ikon + renk ayarı
        when (item.tip) {
            MenuTipi.CORBA -> {
                holder.iconCircle.setBackgroundResource(R.drawable.bg_icon_circle_soup)
                holder.icon.setImageResource(R.drawable.ic_soup)
                holder.icon.setColorFilter(ContextCompat.getColor(context, R.color.green))
                holder.kategoriChip.setBackgroundResource(R.drawable.bg_chip_green)
                holder.kategoriChip.setTextColor(ContextCompat.getColor(context, R.color.dark_green))
                holder.onayIcon.setImageResource(R.drawable.ic_check)
                holder.onayIcon.setColorFilter(ContextCompat.getColor(context, R.color.dark_green))
            }
            MenuTipi.ANA_YEMEK -> {
                holder.iconCircle.setBackgroundResource(R.drawable.bg_icon_circle_meal)
                holder.icon.setImageResource(R.drawable.ic_dish)
                holder.icon.setColorFilter(ContextCompat.getColor(context, R.color.red))
                holder.kategoriChip.setBackgroundResource(R.drawable.bg_chip_red)
                holder.kategoriChip.setTextColor(ContextCompat.getColor(context, R.color.red))
                holder.onayIcon.setImageResource(R.drawable.ic_check)
                holder.onayIcon.setColorFilter(ContextCompat.getColor(context, R.color.red))
            }
            MenuTipi.TATLI -> {
                holder.iconCircle.setBackgroundResource(R.drawable.bg_icon_circle_dessert)
                holder.icon.setImageResource(R.drawable.ic_pie)
                holder.icon.setColorFilter(ContextCompat.getColor(context, R.color.orange))
                holder.kategoriChip.setBackgroundResource(R.drawable.bg_chip_orange)
                holder.kategoriChip.setTextColor(ContextCompat.getColor(context, R.color.orange))
                holder.onayIcon.setImageResource(R.drawable.ic_check)
                holder.onayIcon.setColorFilter(ContextCompat.getColor(context, R.color.orange))
            }

            MenuTipi.SALATA -> {
                holder.iconCircle.setBackgroundResource(R.drawable.bg_icon_circle_salad)
                holder.icon.setImageResource(R.drawable.ic_salad)
                holder.icon.setColorFilter(ContextCompat.getColor(context, R.color.blue))
                holder.kategoriChip.setBackgroundResource(R.drawable.bg_chip_blue)
                holder.kategoriChip.setTextColor(ContextCompat.getColor(context, R.color.blue))
                holder.onayIcon.setImageResource(R.drawable.ic_check)
                holder.onayIcon.setColorFilter(ContextCompat.getColor(context, R.color.blue))
            }
        }

    }

    override fun getItemCount() = items.size
}