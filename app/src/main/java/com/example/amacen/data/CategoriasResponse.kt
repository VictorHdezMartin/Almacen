package com.example.amacen.data

import com.google.gson.annotations.SerializedName

data class CategoriasListResponse(
    @SerializedName("results") val results: List<String>,
){ }

// -------------------------------------------------------------------------------------------------

data class ProductosListResponse(
    @SerializedName("products") val products: List<ProductsClass>,
){ }

data class ProductsClass(
    @SerializedName("category") val category: String,
    @SerializedName("id") val id: Int,
    @SerializedName("title") val title: String,
    @SerializedName("thumbnail") val imagen: String
){ }


// -------------------------------------------------------------------------------------------------

