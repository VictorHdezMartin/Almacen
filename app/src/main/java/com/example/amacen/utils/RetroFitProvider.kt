package com.example.amacen.utils

import com.example.amacen.services.CategoriasService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetroFitProvider {
    companion object {
        fun getRetroFit(): CategoriasService {
            val retroFit = Retrofit.Builder()
                .baseUrl("https://dummyjson.com/products/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            return retroFit.create(CategoriasService::class.java)
        }
    }
}