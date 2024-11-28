package com.example.amacen.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.amacen.data.ProductsClass
import com.example.amacen.databinding.ItemProductoBinding
import com.squareup.picasso.Picasso

class ProductosAdapter (var items: List<ProductsClass>, val onItemClick:(Int) -> Unit) : RecyclerView.Adapter<Producto_ViewHolder>() {

    override fun onBindViewHolder(holder: Producto_ViewHolder, position: Int) {
        val Producto_selected = items[position]

        holder.render(Producto_selected)
        holder.itemView.setOnClickListener{ onItemClick(position) }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Producto_ViewHolder {
        val binding = ItemProductoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return Producto_ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun updateItems(items: List<ProductsClass>) {
        this.items = items
        notifyDataSetChanged()
    }
}

class Producto_ViewHolder(val binding: ItemProductoBinding) : RecyclerView.ViewHolder(binding.root) {
    fun render(Producto: ProductsClass) {
        binding.ProductosTextView.text = Producto.title.substring(0,1).uppercase() +                     // primer en mayúsculas
                                         Producto.title.substring(1, Producto.title.length).lowercase()  // el resto minúsculas

        Picasso.get().load(Producto.imagen).into(binding.thumbnail)                                      // cargamos la imagen del producto
    }
}
