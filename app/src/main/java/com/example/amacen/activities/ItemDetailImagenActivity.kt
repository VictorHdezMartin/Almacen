package com.example.amacen.activities

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.amacen.R
import com.example.amacen.adapters.GalleryAdapter
import com.example.amacen.adapters.ImagenesAdapter
import com.example.amacen.databinding.ActivityItemDetailImagenBinding

import com.squareup.picasso.Picasso

class ItemDetailImagenActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_URL_LIST = "EXTRA_URL_LIST"
        const val EXTRA_SELECTED_URL = "EXTRA_SELECTED_URL"
        const val EXTRA_CABECERA_ID = "EXTRA_CABECERA_ID"
    }

    lateinit var binding: ActivityItemDetailImagenBinding

 // adapter para las imagenes del content_imagenes
    lateinit var galleryAdapter: GalleryAdapter

    var imageList: List<String> = emptyList()
    lateinit var itemArticulo: String
    var selectedPos = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        binding = ActivityItemDetailImagenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        imageList = intent.getStringArrayListExtra(EXTRA_URL_LIST)!!.toList()
        selectedPos = intent.getIntExtra(EXTRA_SELECTED_URL, 0)


    //  CREAMOS EL ADAPTER PARA EL RECYCLERVIEW DE LAS IMAGENES EN EL LAYOUT CONTENT_IMAGENES  -----
        galleryAdapter = GalleryAdapter(imageList, selectedPos, { position ->
            loadImagen(position)
        })

        binding.galleryRecyclerView.adapter = galleryAdapter
        binding.galleryRecyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

     // Listener de los botones  (izquierda / derecha )  recorremos la lista -----------------------
        binding.leftGalleryButton.setOnClickListener( ){
            if (selectedPos > 0)
                selectedPos = selectedPos - 1
            else
                selectedPos = imageList.size - 1

            galleryAdapter.setSelectedItem(selectedPos)
            binding.galleryRecyclerView.scrollToPosition(selectedPos)

            loadImagen(selectedPos)
        }

        binding.rightGalleryButton.setOnClickListener( ){
            if (selectedPos < imageList.size - 1)
                selectedPos = selectedPos + 1
            else
                selectedPos = 0

            galleryAdapter.setSelectedItem(selectedPos)
            binding.galleryRecyclerView.scrollToPosition(selectedPos)
            loadImagen(selectedPos)
        }

        loadImagen(selectedPos)
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

 // Cargo la imagen de la lista  -------------------------------------------------------------------
    fun loadImagen(itemPos: Int) {

     var cabecera = intent.getStringExtra(EXTRA_CABECERA_ID)!!
     binding.cabGallery.setText(cabecera)

     Picasso.get().load(imageList[itemPos]).into(binding.itemImage)
 }
}