package com.example.amacen.activities

import android.content.Intent
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
import com.example.amacen.adapters.ImagenesAdapter
import com.example.amacen.adapters.ReviewsAdapter
import com.example.amacen.data.ArticuloClass
import com.example.amacen.databinding.ActivityProductoDetalleBinding
import com.example.amacen.utils.RetroFitProvider
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class ProductoDetalleActivity: AppCompatActivity() {

    companion object {
        const val EXTRA_CATEGORIA_ID = "EXTRA_CATEGORIA_ID"
        const val EXTRA_PRODUCTO_ID  = "EXTRA_PRODUCTO_ID"
    }

    lateinit var binding: ActivityProductoDetalleBinding
    lateinit var itemArticulo: ArticuloClass

 // adapter para las imagenes del content_imagenes
    lateinit var imagenesAdapter: ImagenesAdapter
    var imagenesList: List<String> = emptyList()
    lateinit var imagenSelect: String

 // adapter para los reviews del content_reviews
    lateinit var reviewsAdapter: ReviewsAdapter
    var reviewList: List<ArticuloClass.Review> = emptyList()
    lateinit var reviewSelect: ArticuloClass.Review

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

        supportActionBar?.setDisplayHomeAsUpEnabled(true)                                           // icono de pantalla anterior en el AcctionBar

        binding.navigationBar.setOnItemSelectedListener { setSelectedTab(it.itemId) }
        binding.navigationBar.selectedItemId = R.id.menu_caracteristicas

        val id = intent.getIntExtra(EXTRA_PRODUCTO_ID,-1)                               // capturamos el nombre de la categoria seleccionada

    //  CREAMOS EL ADAPTER PARA EL RECYCLERVIEW DE LAS IMAGENES EN EL LAYOUT CONTENT_IMAGENES  -----
        imagenesAdapter = ImagenesAdapter(imagenesList, { position ->
            navigateToImagenSelect(position)
        })

        binding.imagenesContent.ImagesRecyclerView.adapter = imagenesAdapter
        binding.imagenesContent.ImagesRecyclerView.layoutManager = GridLayoutManager(this, 2)  // nº columnas

    //  CREAMOS EL ADAPTER PARA EL RECYCLERVIEW DE LOS REVIEWS EN EL LAYOUT CONTENT_REVIEWS
        reviewsAdapter = ReviewsAdapter(reviewList, { position ->
            reviewSelect =  reviewList[position]                                                    // list de los reviews
            //navigateToReviews(reviewSelect)                                                       // si hacemos click navegamos
        })

        binding.reviewsContent.ReviewsRecyclerView.adapter = reviewsAdapter
        binding.reviewsContent.ReviewsRecyclerView.layoutManager = GridLayoutManager(this, 1)  // nº columnas

    // ------
        LoadArticulosFromAPI(id)
    }

// -------------------------------------------------------------------------------------------------
    private fun navigateToReviews(review: ArticuloClass.Review) {
        val intentView = Intent(this, ProductoDetalleActivity::class.java)
        startActivity(intentView)
    }

// Ir a la imagen seleccionada ---------------------------------------------------------------------
    private fun navigateToImagenSelect(selectedPosition: Int) {
        val intent = Intent(this, ItemDetailImagenActivity::class.java)

        intent.putExtra(ItemDetailImagenActivity.EXTRA_SELECTED_URL, selectedPosition)
        intent.putStringArrayListExtra(ItemDetailImagenActivity.EXTRA_URL_LIST, ArrayList(imagenesList))
        intent.putExtra(ItemDetailImagenActivity.EXTRA_CABECERA_ID, binding.cabArticulo.text)
        startActivity(intent)
    }

// volver activity anterior  -----------------------------------------------------------------------
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
                binding.reviewsContent.root.visibility = View.GONE
            }
            R.id.menu_reviews -> {
                binding.caracteristicasContent.root.visibility = View.GONE
                binding.reviewsContent.root.visibility = View.VISIBLE
                binding.imagenesContent.root.visibility = View.GONE
            }
            R.id.menu_imagenes -> {
                binding.caracteristicasContent.root.visibility = View.GONE
                binding.reviewsContent.root.visibility = View.GONE
                binding.imagenesContent.root.visibility = View.VISIBLE
            }
        }
        return true
    }

// Cargamos la ficha del articulo  --------------------------------------------------------------

    private fun LoadArticulosFromAPI(id: Int) {

        val service = RetroFitProvider.getRetroFit()                                                // creamos el objeto para hacer la llamada a la API

        CoroutineScope(Dispatchers.IO).launch {                                                     // hay que hacerlo en un hilo secundario
            try {
                itemArticulo = service.ArticuloListResponse(id)                                     // hacemos la llamada a la API y pasamos la idArticulo

                CoroutineScope(Dispatchers.Main).launch {                                           // llamamos al hilo principal para cargar los datos
                    loadData()
                }
            } catch (e: Exception) {
                Log.e("API", e.stackTraceToString())
            }
        }
    }
// revisar texto de la cabecera, la primera en mayuscula y el resto minusculas  --------------------
    fun revisarCabecera(texto: String): String {
      return texto.substring(0,1).uppercase() +                                                     // primer en mayúsculas
             texto.substring(1, texto.length).lowercase()                                           // el resto minúsculas
    }

// Cargamos los elementos de la ficha del super heroe  ---------------------------------------------
    fun loadData() {
        var categoria = intent.getStringExtra(EXTRA_CATEGORIA_ID)!!

         binding.cabArticulo.text = getString(R.string.articulo_detail, revisarCabecera(categoria), revisarCabecera(itemArticulo.title))

        with (binding) {
            caracteristicasContent.category.text = itemArticulo.category.toString()
            caracteristicasContent.brand.text = itemArticulo.brand.toString()
            caracteristicasContent.descripcion.text = itemArticulo.title.toString()
            caracteristicasContent.title.text = itemArticulo.title.toString()
            caracteristicasContent.price.text = itemArticulo.price.toString()
            caracteristicasContent.returnPolicy.text = itemArticulo.returnPolicy.toString()
            caracteristicasContent.shippingInformation.text = itemArticulo.shippingInformation.toString()
            caracteristicasContent.Sku.text = itemArticulo.sku.toString()
            caracteristicasContent.stock.text = itemArticulo.stock.toString()
            caracteristicasContent.warrantyInformation.text = itemArticulo.warrantyInformation.toString()
            caracteristicasContent.weight.text = itemArticulo.weight.toString()
            caracteristicasContent.minimumOrderQuantity.text = itemArticulo.minimumOrderQuantity.toString()
            caracteristicasContent.rating.text = itemArticulo.rating.toString()
        }

        imagenesList = itemArticulo.images
        imagenesAdapter.updateItems(imagenesList)

        reviewList = itemArticulo.reviews
        reviewsAdapter.updateItems(reviewList)
    }
}