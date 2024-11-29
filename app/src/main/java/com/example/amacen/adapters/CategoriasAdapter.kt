package com.example.amacen.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.amacen.databinding.ItemCategoriaBinding
import com.squareup.picasso.Picasso

class CategoriasAdapter (var items: List<String>, val onItemClick:(Int) -> Unit) : RecyclerView.Adapter<Categoria_ViewHolder>() {

       override fun onBindViewHolder(holder: Categoria_ViewHolder, position: Int) {

        val Categoria_selected = items[position]

        holder.render(Categoria_selected, items.size, position)
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

// -----------------------------

class Categoria_ViewHolder(val binding: ItemCategoriaBinding) : RecyclerView.ViewHolder(binding.root) {
    var URLs = listOf("https://cdn.dummyjson.com/products/images/beauty/Eyeshadow%20Palette%20with%20Mirror/thumbnail.png",
                      "https://cdn.dummyjson.com/products/images/fragrances/Chanel%20Coco%20Noir%20Eau%20De/1.png",
                      "https://cdn.dummyjson.com/products/images/furniture/Annibale%20Colombo%20Sofa/1.png",
                      "https://cdn.dummyjson.com/products/images/groceries/Apple/1.png",
                      "https://cdn.dummyjson.com/products/images/home-decoration/Decoration%20Swing/1.png",
                      "https://cdn.dummyjson.com/products/images/kitchen-accessories/Bamboo%20Spatula/1.png",
                      "https://cdn.dummyjson.com/products/images/laptops/Apple%20MacBook%20Pro%2014%20Inch%20Space%20Grey/1.png",
                      "https://cdn.dummyjson.com/products/images/mens-shirts/Blue%20&%20Black%20Check%20Shirt/1.png",
                      "https://cdn.dummyjson.com/products/images/mens-shoes/Nike%20Air%20Jordan%201%20Red%20And%20Black/1.png",
                      "https://cdn.dummyjson.com/products/images/mens-watches/Brown%20Leather%20Belt%20Watch/1.png",
                      "https://cdn.dummyjson.com/products/images/mobile-accessories/Apple%20Airpods/1.png",
                      "https://cdn.dummyjson.com/products/images/motorcycle/Generic%20Motorcycle/1.png",
                      "https://cdn.dummyjson.com/products/images/skin-care/Olay%20Ultra%20Moisture%20Shea%20Butter%20Body%20Wash/1.png",
                      "https://cdn.dummyjson.com/products/images/smartphones/iPhone%205s/1.png",
                      "https://cdn.dummyjson.com/products/images/sports-accessories/Baseball%20Ball/1.png",
                      "https://cdn.dummyjson.com/products/images/sunglasses/Classic%20Sun%20Glasses/1.png",
                      "https://cdn.dummyjson.com/products/images/tablets/Samsung%20Galaxy%20Tab%20White/1.png",
                      "https://cdn.dummyjson.com/products/images/tops/Blue%20Frock/1.png",
                      "https://cdn.dummyjson.com/products/images/vehicle/300%20Touring/1.png",
                      "https://cdn.dummyjson.com/products/images/womens-bags/Heshe%20Women's%20Leather%20Bag/1.png",
                      "https://cdn.dummyjson.com/products/images/womens-dresses/Black%20Women's%20Gown/1.png",
                      "https://cdn.dummyjson.com/products/images/womens-jewellery/Tropical%20Earring/1.png",
                      "https://cdn.dummyjson.com/products/images/womens-shoes/Golden%20Shoes%20Woman/1.png",
                      "https://cdn.dummyjson.com/products/images/womens-watches/Rolex%20Cellini%20Moonphase/1.png")

    fun render(categoria: String, tamano: Int, position: Int) {
        binding.CategoriasTextView.text = "   " + revisarCabecera(categoria)
        Picasso.get().load(URLs[position]).into(binding.CategoriasThumbnail)
    }
}
