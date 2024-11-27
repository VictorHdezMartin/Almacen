package com.example.amacen.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.GridLayoutManager
import com.example.amacen.adapters.ProductosAdapter
import com.example.amacen.data.ProductsClass
import com.example.amacen.databinding.ActivityProductosBinding
import com.example.amacen.utils.RetroFitProvider
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ProductosActivity : AppCompatActivity() {

    lateinit var binding: ActivityProductosBinding
    lateinit var adapter: ProductosAdapter
    var productosList: List<ProductsClass> = emptyList()              // cargamos el listado de PRODUCTOS

    companion object {
        const val EXTRA_CATEGORIA_ID = "EXTRA_CATEGORIA_ID"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityProductosBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        adapter= ProductosAdapter(productosList, { position -> val id_Producto =  productosList[position].id
        navigateToDetailProducto(id_Producto) })

        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = GridLayoutManager(this, 2)  // nº columnas

        LoadProductosAPI()
    }

// Vamos al activity de Productos, pasando el parametro de busqueda de los productos de la categoria

    private fun navigateToDetailProducto(id_Product: Int) { val intent = Intent(this, ProductoDetalleActivity::class.java)
        intent.putExtra(ProductoDetalleActivity.EXTRA_PRODUCTO_ID, id_Product)
        startActivity(intent)
    }

// Cargamos los datos del las CATEGORIAS de la API  ------------------------------------------------

    private fun LoadProductosAPI() {

        val service = RetroFitProvider.getRetroFit()                                                // aquí hacemos la consulta

        var categoria = intent.getStringExtra(EXTRA_CATEGORIA_ID)!!

        binding.productosCab.setText("${binding.productosCab.text} : ${categoria}")

        CoroutineScope(Dispatchers.IO).launch {                                    // hay que ejecutar la consulta en un hilo secundario
            try {
                val result = service.ProductosListResponse(categoria)

                CoroutineScope(Dispatchers.Main).launch {                          // volvemos al hilo principal para mostrar resultados
                    if (result.products.isEmpty()) {
                        // Toast.makeText(this, "No se han encontrado resultados", Toast.LENGTH_SHORT).show()
                    } else {
                        productosList = result.products
                        adapter.updateItems(productosList)
                    }
                }
            } catch (e: Exception) {
                Log.e("API", e.stackTraceToString())
            }
        }
    }
}