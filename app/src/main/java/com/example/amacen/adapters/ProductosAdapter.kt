package com.example.amacen.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.amacen.R
import com.example.amacen.data.ProductsClass
import com.example.amacen.databinding.ItemProductoBinding
import com.example.amacen.utils.SessionManager
import com.squareup.picasso.Picasso

class ProductosAdapter (var items: List<ProductsClass>,
                        val onItemClick:(Int) -> Unit,
                        val onFavoriteClick:(Int) -> Unit) : RecyclerView.Adapter<Producto_ViewHolder>() {

    override fun onBindViewHolder(holder: Producto_ViewHolder, position: Int) {
        val Producto_selected = items[position]

        holder.render(Producto_selected)
        holder.itemView.setOnClickListener{
            onItemClick(position)
            holder.VisitasProducto(Producto_selected) }

        holder.binding.favoritosIMG.setOnClickListener {
            onFavoriteClick(position)
            holder.setFavoriteIcon(Producto_selected) }
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

// revisar texto de la cabecera, la primera en mayuscula y el resto minusculas  --------------------
   fun revisarCabecera(texto: String): String {
       return texto.substring(0, 1).uppercase() +                                                     // primer en mayúsculas
              texto.substring(1, texto.length).lowercase()                                           // el resto minúsculas
   }

// -------------

class Producto_ViewHolder(val binding: ItemProductoBinding) : RecyclerView.ViewHolder(binding.root) {

    val context = itemView.context
    fun render(Producto: ProductsClass) {
        binding.ProductosTextView.text = revisarCabecera(Producto.title)

        Picasso.get().load(Producto.imagen).into(binding.thumbnail)                                 // cargamos la imagen del producto

        setFavoriteIcon(Producto)

        var visitas = SessionManager(context).getVisitasProducto(Producto.id)
        if (visitas != 0)
            binding.visitasProducto.text = SessionManager(context).getVisitasProducto(Producto.id).toString()
    }

    fun setFavoriteIcon(Producto: ProductsClass) {
        var isFavorite = SessionManager(context).getFavoriteProducto(Producto.id)
        if (isFavorite) {
            binding.favoritosIMG.setImageResource(R.drawable.icono_like_select)
            //binding.favoritosIMG.setColorFilter(context.getColor(R.color.rojo))

        } else {
            binding.favoritosIMG.setImageResource(R.drawable.icono_like_unselect)
            //binding.favoritosIMG.setColorFilter(context.getColor(R.color.azul))
        }
    }
    fun VisitasProducto(Producto: ProductsClass) {
        SessionManager(context).addVisitasProducto(Producto.id)
        var visitas = SessionManager(context).getVisitasProducto(Producto.id)
        if (visitas != 0)
            binding.visitasProducto.text = SessionManager(context).getVisitasProducto(Producto.id).toString()
    }
}
