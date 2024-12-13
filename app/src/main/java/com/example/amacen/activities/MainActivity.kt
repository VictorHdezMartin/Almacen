package com.example.amacen.activities

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
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
import androidx.appcompat.widget.SearchView
import com.example.amacen.data.CategoriasClass

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    lateinit var adapter: CategoriasAdapter

    // manejo de categorias
    var categoriasList: List<String> =
        emptyList()                                                  // cargamos el listado de CATEGORIAS de internet
    var categoriasClassList: MutableList<CategoriasClass> =
        mutableListOf()                         // con esta jugamos
    var categoriasClassListBackup: MutableList<CategoriasClass> =
        mutableListOf()                   // backup para recuperar al buscar
    var ordenCategorias: Boolean = true

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

        supportActionBar?.setDisplayHomeAsUpEnabled(true)                                           // icono de pantalla anterior en el AcctionBar

        adapter = CategoriasAdapter(categoriasClassList, { position ->
            val categoria = categoriasClassList[position]
            navigateToProductos(categoria, position)
        })

        binding.CategoriasRecyclerView.adapter = adapter
        binding.CategoriasRecyclerView.layoutManager = GridLayoutManager(this, 2)  // nº columnas

        LoadCategoriasAPI()
    }

    // Menu de opciones  -------------------------------------------------------------------------------
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main_activity, menu)

        val menuItem = menu?.findItem(R.id.menu_buscarCategoria)!!
        val searchView = menuItem.actionView as SearchView

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                Buscar_Categoria(query)
                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {
                return false
            }
        })

        return true
    }

    // vemos que item se ha seleccionado  -------------------------------------------------------------
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                finish()
                return true
            }

            R.id.menu_orden_ascendente -> {
                Orden_Ascendente()
                return true
            }

            R.id.menu_orden_descendente -> {
                Orden_Descendente()
                return true
            }

            else -> {
                return super.onOptionsItemSelected(item)
            }
        }
    }

    // Buscamos categorías según el filtro de búsqueda  ------------------------------------------------
    fun Buscar_Categoria(filtro: String) {
        var filterCategorias: List<CategoriasClass>

        categoriasClassList =
            categoriasClassListBackup                                             // recuperamos la lista original
        ordenCategorias = !ordenCategorias

        if (filtro != "%") {
            filterCategorias =
                categoriasClassList.filter { it.nombreCategoria.contains("${filtro}", true) }
            categoriasClassList = (filterCategorias as MutableList<CategoriasClass>).toMutableList()
        }

        adapter.updateItems(categoriasClassList)                                                    // aplicamos el filtro de búsqueda Categoria al adapter
        nTotalCategorias()
    }

// -------------------------------------------------------------------------------------------------

    fun nTotalCategorias() {
       binding.TotCategorias.text = categoriasClassList.size.toString()
    }

// -------
    fun Orden_Ascendente() {
        if (!ordenCategorias) {
            ordenCategorias = !ordenCategorias
            adapter.updateItems(categoriasClassList.sortedBy { it.nombreCategoria })
        }
    }
// -------
    fun Orden_Descendente() {
        if (ordenCategorias) {
            ordenCategorias = !ordenCategorias
            adapter.updateItems(categoriasClassList.sortedByDescending { it.nombreCategoria })
        }
    }

 // Vamos al activity de Productos, pasando el nombre de la categoría

    private fun navigateToProductos(categoria: CategoriasClass, position: Int) {
        val intent = Intent(this, ProductosActivity::class.java)
        intent.putExtra(ProductosActivity.EXTRA_CATEGORIA_ID, categoria.nombreCategoria)
        startActivity(intent)
    }

// Método para mostrar la notificación al finalizar la aplicación  ---------------------------------

    private fun NotificacionExistAPP() {
        // Create a notification channel (required for Android 8.0 and above)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                "${R.string.FinalizarAPP_titulo}", "${R.string.FinalizarAPP_mensaje}", NotificationManager.IMPORTANCE_DEFAULT
            )
            val manager: NotificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            manager.createNotificationChannel(channel)
        }

     // Build the notification
        val notification = NotificationCompat.Builder(this, "exit_channel")
            .setContentTitle("App is exiting")
            .setContentText("You have left the app.")
            .setSmallIcon(R.drawable.icono_finalizar)               // Replace with your app's icon
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .build()

     // Show the notification
        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.notify(1, notification)
    }

// Cargamos los datos del las CATEGORIAS de la API  ------------------------------------------------

    private fun LoadCategoriasAPI() {

        val service = RetroFitProvider.getRetroFit()                                                // preparamos la consulta

        CoroutineScope(Dispatchers.IO).launch {                                                     // hay que ejecutar la consulta en un hilo secundario
            try {
                val result = service.ListCategoriasResponse()

                CoroutineScope(Dispatchers.Main).launch {                                           // volvemos al hilo principal para mostrar resultados
                    if (result.isEmpty() ) {
                        // Toast.makeText(this, "No se han encontrado resultados", Toast.LENGTH_SHORT).show()
                    } else {
                        categoriasList = result
                        LoadImgUrlCategorias()
                        nTotalCategorias()
                        adapter.updateItems(categoriasClassList)
                    }
                }
            } catch (e: Exception) {
                Log.e("API", e.stackTraceToString())
            }
        }
    }

// Cargamos Nombre y Url de Categoria --------------------------------------------------------------

   private fun LoadImgUrlCategorias() {
       val URLs = listOf(
           "https://cdn.dummyjson.com/products/images/beauty/Eyeshadow%20Palette%20with%20Mirror/thumbnail.png",
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
           "https://cdn.dummyjson.com/products/images/womens-watches/Rolex%20Cellini%20Moonphase/1.png"
       )

       for (index in categoriasList.indices) {
           categoriasClassList.add(CategoriasClass(categoriasList[index], URLs[index]))
       }
       categoriasClassListBackup = categoriasClassList                                              // hacemos la copia de respaldo
   }
}
