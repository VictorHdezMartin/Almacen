package com.example.amacen.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.amacen.R
import com.example.amacen.databinding.ItemGalleryBinding
import com.example.amacen.databinding.ItemImagenBinding
import com.squareup.picasso.Picasso

class GalleryAdapter (var imgList: List<String>, var selectedImage: Int, val onItemClick:(Int) -> Unit) : RecyclerView.Adapter<Gallery_ViewHolder>() {

    override fun onBindViewHolder(holder: Gallery_ViewHolder, position: Int) {
        val itemGallery = imgList[position]

        holder.render(itemGallery, position == selectedImage)
        holder.itemView.setOnClickListener{
            onItemClick(position)

            setSelectedItem(position)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Gallery_ViewHolder {
        val binding = ItemGalleryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return Gallery_ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return imgList.size
    }

// al cambiar el itemGallery, cambiamos el color de fondo de la cardview seleccionada  -------------
    fun setSelectedItem(position: Int) {
        val lastSelected = selectedImage  // posicion actual para quitar el color de selccion
        selectedImage = position          // nueva posicionpara poner color de fondo

        notifyItemChanged(lastSelected)   // actualizamos para cambiar color
        notifyItemChanged(selectedImage)  // actualizamos para cambiar color
    }

// actualizamos el adapter con cada item -----------------------------------------------------------
    fun updateItems(items: List<String>) {
        this.imgList = items
        notifyDataSetChanged()
    }
}

// pintamos cada item en el recyclerview  ----------------------------------------------------------
class Gallery_ViewHolder(val binding: ItemGalleryBinding) :  RecyclerView.ViewHolder(binding.root) {
    fun render(url: String, isSelected: Boolean) {
        Picasso.get().load(url).into(binding.detalleGallery)

       val context = itemView.context

        if (isSelected) {
            binding.root.setBackgroundColor(context.getColor(R.color.amarilloFlojo))
        } else {
            binding.root.setBackgroundColor(context.getColor(R.color.white))
        }
    }
}
