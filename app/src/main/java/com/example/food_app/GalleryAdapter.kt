// GalleryAdapter.kt
package com.example.food_app

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.food_app.databinding.GalleryItemBinding

class GalleryAdapter(private val photos: List<String>) :
    RecyclerView.Adapter<GalleryAdapter.VH>() {

    inner class VH(val binding: GalleryItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val binding = GalleryItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return VH(binding)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.binding.ivGalleryPhoto.load(photos[position])
    }

    override fun getItemCount() = photos.size
}