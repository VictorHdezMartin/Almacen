package com.example.amacen.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.amacen.databinding.ItemCategoriaBinding

class CategoriasAdapter (var items: List<String>, val onItemClick:(Int) -> Unit) : RecyclerView.Adapter<Categoria_ViewHolder>() {

       override fun onBindViewHolder(holder: Categoria_ViewHolder, position: Int) {

        val Categoria_selected = items[position]

        holder.render(Categoria_selected)
        holder.itemView.setOnClickListener{onItemClick(position)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Categoria_ViewHolder {
        val binding = ItemCategoriaBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return Categoria_ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun updateItems(items: List<String>) {
        this.items = items
        notifyDataSetChanged()
    }
}

class Categoria_ViewHolder(val binding: ItemCategoriaBinding) : RecyclerView.ViewHolder(binding.root) {
    fun render(categoria: String) {
        binding.CategoriasTextView.text = "    " + categoria.substring(0,1).uppercase() +                // primer en mayúsculas
                                                   categoria.substring(1, categoria.length).lowercase()  // el resto minúsculas
    }
}