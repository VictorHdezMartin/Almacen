package com.example.amacen.activities

import android.content.Intent
import android.icu.text.CaseMap.Title
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.GridLayoutManager
import com.example.amacen.R
import com.example.amacen.adapters.ProductosAdapter
import com.example.amacen.data.ProductsClass
import com.example.amacen.databinding.ActivityProductosBinding
import com.example.amacen.utils.RetroFitProvider
import com.example.amacen.utils.SessionManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ProductosActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_CATEGORIA_ID = "EXTRA_CATEGORIA_ID"
    }

    lateinit var binding: ActivityProductosBinding
    lateinit var adapter: ProductosAdapter
    lateinit var categoria: String

    lateinit var sessionManager: SessionManager

    var productosList: List<ProductsClass> = emptyList()                                            // cargamos el listado de PRODUCTO
    var filterProductos: List<ProductsClass> = emptyList()
    var productosListBackup: List<ProductsClass> = emptyList()                                      // copia de respaldo
    var ordenProductos: Boolean = true

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

        supportActionBar?.setDisplayHomeAsUpEnabled(true)                                           // icono de pantalla anterior en el AcctionBar

        categoria = intent.getStringExtra(EXTRA_CATEGORIA_ID)!!                                     // pasamos la CATEGORIA seleccionada

        adapter= ProductosAdapter(productosList, { position ->
                                                        val idProducto =  productosList[position].id
                                                        navigateToDetailProducto(idProducto, categoria)
                                                 }, { position ->                                   // favoritos
                                                        val idProducto =  productosList[position].id
                                                        sessionManager.toggleFavorite(idProducto)
                                                 })

        binding.ProductosRecyclerView.adapter = adapter
        binding.ProductosRecyclerView.layoutManager = GridLayoutManager(this, 2)          // nº columnas

        LoadProductosAPI()

        sessionManager = SessionManager(this)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_productos_activity, menu)

        val menuItem = menu?.findItem(R.id.menu_buscarProducto)!!
        val searchView = menuItem.actionView as SearchView

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                Buscar_Producto(query)
                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {
                return false
            }
        })

        return true
    }

 // vemos que item se ha seleccionado
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
         when (item.itemId ) {
             android.R.id.home -> {
                 finish()
             return true
         }

         R.id.menu_orden_ascendente -> {
             Orden_Ascendente(filterProductos)
             return true
         }

         R.id.menu_orden_descendente -> {
             Orden_Descendente(filterProductos)
             return true
         }

         else -> {
             return super.onOptionsItemSelected(item)
         }
     }
 }

// -------------------------------------------------------------------------------------------------

    fun nTotalProductos() {
        binding.TotProductos.text = productosList.size.toString()
    }

// -------
    fun Buscar_Producto(filtro: String) {
        var filterProductos: List<ProductsClass>
        productosList = productosListBackup
        ordenProductos = !ordenProductos

        if (filtro != "%") {
            filterProductos = productosList.filter { it.title.contains("${filtro}", true) }
            productosList = filterProductos
        }

        adapter.updateItems(productosList)

        nTotalProductos()
    }

// -------
    fun Orden_Ascendente(filterProductos: List<ProductsClass>) {
        if (!ordenProductos) {
            ordenProductos = !ordenProductos
            adapter.updateItems(productosList.sortedBy {it.title})
        }
    }

// -------
    fun Orden_Descendente(filterProductos: List<ProductsClass>) {
        if (ordenProductos) {
            ordenProductos = !ordenProductos
            adapter.updateItems(productosList.sortedByDescending {it.title})
        }
    }

// Vamos al activity de Productos, pasando el parametro de busqueda de los productos de la categoria
    private fun navigateToDetailProducto(idProducto: Int, idCategoria: String) {
        val intent = Intent(this, ProductoDetalleActivity::class.java)
        intent.putExtra(ProductoDetalleActivity.EXTRA_CATEGORIA_ID, idCategoria)
        intent.putExtra(ProductoDetalleActivity.EXTRA_PRODUCTO_ID, idProducto)
        startActivity(intent)
    }

 // revisar texto de la cabecera, la primera en mayuscula y el resto minusculas  --------------------
    fun revisarCabecera(texto: String): String {
        return texto.substring(0,1).uppercase() +                                                   // primer en mayúsculas
                texto.substring(1, texto.length).lowercase()                                        // el resto minúsculas
    }
// Cargamos los datos del las CATEGORIAS de la API  ------------------------------------------------

    private fun LoadProductosAPI() {

        val service = RetroFitProvider.getRetroFit()                                                // aquí hacemos la consulta

        binding.productosCab.text = getString(R.string.productos, revisarCabecera(categoria))

        CoroutineScope(Dispatchers.IO).launch {                                                     // hay que ejecutar la consulta en un hilo secundario
            try {
                val result = service.ListProductosResponse(categoria)

                CoroutineScope(Dispatchers.Main).launch {                                           // volvemos al hilo principal para mostrar resultados
                    if (result.products.isEmpty()) {
                        // Toast.makeText(this, "No se han encontrado resultados", Toast.LENGTH_SHORT).show()
                    } else {
                        productosList = result.products
                        productosListBackup = productosList
                        nTotalProductos()
                        adapter.updateItems(productosList)
                    }
                }
            } catch (e: Exception) {
                Log.e("API", e.stackTraceToString())
            }
        }
    }
}