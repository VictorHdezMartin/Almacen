package com.example.amacen.services

import com.example.amacen.data.CategoriasListResponse
import com.example.amacen.data.ProductosListResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface CategoriasService {


//  llamada para obtener todas las categorias   (OK) -----------------------------------------------
        @GET("category-list")
        suspend fun CategoriasListResponse() : List<String>

// llamada para obtener todos los productos   (OK)  ------------------------------------------------
        @GET("category/{categoria}")
        suspend fun ProductosListResponse(@Path("categoria") categoria: String) : ProductosListResponse

// llamada al producto seleccionado     (?)  -------------------------------------------------------
        @GET("product-item")
        suspend fun ProductoItemResponse() : List<String>

}