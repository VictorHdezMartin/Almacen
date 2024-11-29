package com.example.amacen.utils

import android.content.Context
import android.content.SharedPreferences

class SessionManager(context: Context) {
    companion object {
        const val FAVORITE_PRODUCTO = "FAVORITE_PRODUCTO"
        const val VISITAS_PRODUCTO = "VISITAS_PRODUCTO"
    }

    private val sharedPref: SharedPreferences

    init {
        sharedPref = context.getSharedPreferences("almacen_session", Context.MODE_PRIVATE)
    }

 // Funcionalidad para Favoritos  ------------------------------------------------------------------
    fun setFavoriteProducto(id: Int, isFavorite: Boolean) {
        val editor = sharedPref.edit()
        editor.putBoolean("${FAVORITE_PRODUCTO}_$id", isFavorite)
        editor.apply()
    }

    fun getFavoriteProducto(id: Int): Boolean {
        return sharedPref.getBoolean("${FAVORITE_PRODUCTO}_$id", false)
    }

    fun toggleFavorite(idProducto: Int) {
        setFavoriteProducto(idProducto, !getFavoriteProducto(idProducto))
    }

 // Funcionalidad para las Visitas  ----------------------------------------------------------------
    fun setVisitasProducto(id: Int, visitas: Int) {
        val editor = sharedPref.edit()
        editor.putInt("${VISITAS_PRODUCTO}_$id", visitas)
        editor.apply()
    }

    fun getVisitasProducto(id: Int): Int {
        return sharedPref.getInt("${VISITAS_PRODUCTO}_$id", 0)
    }

    fun addVisitasProducto(id: Int) {
        setVisitasProducto(id, getVisitasProducto(id) + 1)
    }
}