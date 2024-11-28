package com.example.amacen.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.GridLayoutManager
import com.example.amacen.R
import com.example.amacen.adapters.CategoriasAdapter
import com.example.amacen.databinding.ActivityMainBinding
import com.example.amacen.utils.RetroFitProvider
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    lateinit var adapter: CategoriasAdapter
    var categoriasList: List<String> = emptyList()              // cargamos el listado de CATEGORIAS

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        adapter = CategoriasAdapter(categoriasList, { position ->
            val categoria = categoriasList[position]
            navigateToProductos(categoria)
        })

        binding.CategoriasRecyclerView.adapter = adapter
        binding.CategoriasRecyclerView.layoutManager = GridLayoutManager(this, 2)          // nº columnas

        LoadCategoriasAPI()
    }

// Vamos al activity de Productos, pasando el parametro de busqueda de los productos de la categoria

    private fun navigateToProductos(categoria: String) {
        val intent = Intent(this, ProductosActivity::class.java)
        intent.putExtra(ProductosActivity.EXTRA_CATEGORIA_ID, categoria)
        startActivity(intent)
    }

// Cargamos los datos del las CATEGORIAS de la API  ------------------------------------------------

    private fun LoadCategoriasAPI() {

        val service = RetroFitProvider.getRetroFit()                                                // aquí hacemos la consulta

        CoroutineScope(Dispatchers.IO).launch {                                                     // hay que ejecutar la consulta en un hilo secundario
            try {
                val result = service.CategoriasListResponse()

                CoroutineScope(Dispatchers.Main).launch {                                           // volvemos al hilo principal para mostrar resultados
                    if (result.isEmpty()) {
                        // Toast.makeText(this, "No se han encontrado resultados", Toast.LENGTH_SHORT).show()
                    } else {
                        categoriasList = result
                        adapter.updateItems(categoriasList)
                    }
                }
            } catch (e: Exception) {
                Log.e("API", e.stackTraceToString())
            }
        }
    }
}