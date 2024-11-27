package com.example.amacen.activities

import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.GridLayoutManager
import com.example.amacen.R
import com.example.amacen.adapters.CategoriasAdapter
import com.example.amacen.adapters.ImagenesAdapter
import com.example.amacen.data.ArticuloClass
import com.example.amacen.databinding.ActivityProductoDetalleBinding
import com.example.amacen.utils.RetroFitProvider
import com.squareup.picasso.Picasso
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class ProductoDetalleActivity: AppCompatActivity() {

    companion object {
        const val EXTRA_CATEGORIA_ID = "EXTRA_CATEGORIA_ID"
        const val EXTRA_PRODUCTO_ID = "EXTRA_PRODUCTO_ID"
    }

    lateinit var binding: ActivityProductoDetalleBinding
    lateinit var itemArticulo: ArticuloClass
    lateinit var adapter: ImagenesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        binding = ActivityProductoDetalleBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        binding.navigationBar.setOnItemSelectedListener {
            setSelectedTab(it.itemId)
        }

        binding.navigationBar.selectedItemId = R.id.menu_caracteristicas


        val id = intent.getIntExtra(EXTRA_PRODUCTO_ID, -1)



        adapter = CategoriasAdapter(categoriasList, { position -> val categoria = categoriasList[position]
            navigateToProductos(categoria) })

        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = GridLayoutManager(this, 1)  // nº columnas

        getArticulo(id)
    }

// Pestaña seleccionada  ---------------------------------------------------------------------------
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                finish()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

// Activamos la pestaña selccionadad ---------------------------------------------------------------

    fun setSelectedTab(itemId: Int) : Boolean {
        when (itemId) {
            R.id.menu_caracteristicas -> {
                binding.caracteristicasContent.root.visibility = View.VISIBLE
                binding.imagenesContent.root.visibility = View.GONE
                binding.mediasContent.root.visibility = View.GONE
            }
            R.id.menu_medidas -> {
                binding.caracteristicasContent.root.visibility = View.GONE
                binding.mediasContent.root.visibility = View.VISIBLE
                binding.imagenesContent.root.visibility = View.GONE
            }
            R.id.menu_imagenes -> {
                binding.caracteristicasContent.root.visibility = View.GONE
                binding.mediasContent.root.visibility = View.GONE
                binding.imagenesContent.root.visibility = View.VISIBLE
            }
        }
        return true
    }

// Cargamos la ficha del super heroe  --------------------------------------------------------------
    private fun getArticulo(id: Int) {

        val service = RetroFitProvider.getRetroFit()                                  // creamos el objeto para hacer la llamada a la API

        CoroutineScope(Dispatchers.IO).launch {                      // hay que hacerlo en un hilo secundario
            try {
                itemArticulo = service.ArticuloListResponse(id)                      // hacemos la llamada a la API y pasamos la idArticulo

                CoroutineScope(Dispatchers.Main).launch {          // llamamos al hilo principal para cargar los datos
                    loadData()
                }
            } catch (e: Exception) {
                Log.e("API", e.stackTraceToString())
            }
        }
    }

// Cargamos los elementos de la ficha del super heroe  ---------------------------------------------
    fun loadData() {

        var categoria = intent.getStringExtra(EXTRA_CATEGORIA_ID)!!
        var producto = itemArticulo.title

        //binding.cabArticulo.text = "${binding.cabArticulo.text} ${producto} ] ${articulo} "
        binding.cabArticulo.text = getString(R.string.articulo_detail, categoria, producto)

       // Picasso.get().load(superhero.image.url).into(binding.avatarImageView)
        /*
                with (binding) {
                    unoTextView.setText(superhero.biography.fullName)
                    dosTextView.setText(superhero.biography.placeOfBirth)
                    tresTextView.setText(superhero.work.base)
                    cuatroTextView.setText(superhero.work.occupation)
                }
                */
    }






}