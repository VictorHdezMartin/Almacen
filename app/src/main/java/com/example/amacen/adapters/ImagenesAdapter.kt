package com.example.amacen.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.amacen.databinding.ItemImagenBinding
import com.squareup.picasso.Picasso

class ImagenesAdapter (var items: List<String>, val onItemClick:(Int) -> Unit) : RecyclerView.Adapter<Imagenes_ViewHolder>() {

    override fun onBindViewHolder(holder: Imagenes_ViewHolder, position: Int) {
        val selectArticulo = items[position]

        holder.render(selectArticulo)
        holder.itemView.setOnClickListener{onItemClick(position)
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Imagenes_ViewHolder {
        val binding = ItemImagenBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return Imagenes_ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun updateItems(items: List<String>) {
        this.items = items
        notifyDataSetChanged()
    }
}

class Imagenes_ViewHolder(val binding: ItemImagenBinding) : RecyclerView.ViewHolder(binding.root) {
    fun render(url : String) {
        Picasso.get().load(url).into(binding.articuloImage)
    }


}