package com.example.amacen.services

import com.example.amacen.data.ArticuloClass
import com.example.amacen.data.ProductosListResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface CategoriasService {

//  llamada para obtener todas las categorias   (OK) -----------------------------------------------
        @GET("category-list")
        suspend fun ListCategoriasResponse() : List<String>

// llamada para obtener todos los productos   (OK)  ------------------------------------------------
        @GET("category/{categoria}")
        suspend fun ListProductosResponse(@Path("categoria") categoria: String) : ProductosListResponse

// llamada al articulo seleccionado     (OK)  -------------------------------------------------------
        @GET("{idArticulo}")
        suspend fun ArticuloListResponse(@Path("idArticulo") idArticulo: Int) : ArticuloClass


}