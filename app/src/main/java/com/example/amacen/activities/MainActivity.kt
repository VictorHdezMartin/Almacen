package com.example.amacen.activities

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.View
import android.view.MenuItem
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.GridLayoutManager
import com.example.amacen.R
import com.example.amacen.adapters.CategoriasAdapter
import com.example.amacen.adapters.ordenCategorias
import com.example.amacen.databinding.ActivityMainBinding
import com.example.amacen.utils.RetroFitProvider
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import androidx.appcompat.widget.SearchView

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    lateinit var adapter: CategoriasAdapter
    var categoriasList: List<String> = emptyList()                                                  // cargamos el listado de CATEGORIAS

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

        adapter = CategoriasAdapter(categoriasList, { position ->
           val categoria = categoriasList[position]
            navigateToProductos(categoria, position)
        })

        binding.CategoriasRecyclerView.adapter = adapter
        binding.CategoriasRecyclerView.layoutManager = GridLayoutManager(this, 2)  // nº columnas

        LoadCategoriasAPI()

        Buscar_Categoria("a")
    }

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

 // vemos que item se ha seleccionado
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId ) {
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

// -------
    fun Buscar_Categoria(filtro: String) {
        var filterCategorias: List<String>
        if (filtro == "%")
            filterCategorias = categoriasList
        else
            filterCategorias = categoriasList.filter { it.contains("${filtro}", true) }

        adapter.updateItems(filterCategorias)                                                       // aplicamos el filtro de búsqueda Categoria al adapter
    }

// -------
    fun Orden_Ascendente() {
        if (!ordenCategorias) {
            ordenCategorias = !ordenCategorias
            adapter.updateItems(categoriasList.sorted())
        }
    }
// -------
    fun Orden_Descendente() {
        if (ordenCategorias) {
            ordenCategorias = !ordenCategorias
            adapter.updateItems(categoriasList.sortedDescending())
        }
    }

 // Vamos al activity de Productos, pasando el parametro de busqueda de los productos de la categoria
    private fun navigateToProductos(categoria: String, position: Int) {
        val intent = Intent(this, ProductosActivity::class.java)

        var sortCategoria = categoriasList.sorted()
        if (!ordenCategorias)
            sortCategoria = categoriasList.sortedDescending()

        intent.putExtra(ProductosActivity.EXTRA_CATEGORIA_ID, sortCategoria[position])
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
