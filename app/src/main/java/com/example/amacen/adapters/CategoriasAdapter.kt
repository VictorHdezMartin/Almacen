package com.example.amacen.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.amacen.data.CategoriasClass
import com.example.amacen.databinding.ItemCategoriaBinding
import com.squareup.picasso.Picasso

class CategoriasAdapter (var items: List<CategoriasClass>, val onItemClick:(Int) -> Unit) : RecyclerView.Adapter<Categoria_ViewHolder>() {

    override fun onBindViewHolder(holder: Categoria_ViewHolder, position: Int) {

       val idCategoria = items[position]
       holder.render(idCategoria)
       holder.itemView.setOnClickListener { onItemClick(position) }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Categoria_ViewHolder {
        val binding = ItemCategoriaBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return Categoria_ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun updateItems(items: List<CategoriasClass>) {
        this.items = items
        notifyDataSetChanged()
    }
}

// -----------------------------

class Categoria_ViewHolder(val binding: ItemCategoriaBinding) : RecyclerView.ViewHolder(binding.root) {

     fun render(categoria: CategoriasClass) {
        binding.CategoriasTextView.text = revisarCabecera(categoria.nombreCategoria)
        categoria.urlCategoria?.let { binding.CategoriasThumbnail.setImageBitmap(it) }

      //  Picasso.get().load(categoria.urlCategoria).into(binding.CategoriasThumbnail)
    }
}
