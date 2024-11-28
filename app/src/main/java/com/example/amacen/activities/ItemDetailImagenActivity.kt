package com.example.amacen.activities

import android.os.Bundle
import android.view.MenuItem
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.amacen.databinding.ActivityItemDetailImagenBinding

import com.squareup.picasso.Picasso

class ItemDetailImagenActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_URL_LIST = "EXTRA_URL_LIST"
        const val EXTRA_SELECTED_URL = "EXTRA_SELECTED_URL"
    }

    lateinit var binding: ActivityItemDetailImagenBinding

    lateinit var imagenesList: List<String>
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

        imagenesList = intent.getStringArrayListExtra(EXTRA_URL_LIST)!!.toList()
        selectedPos = intent.getIntExtra(EXTRA_SELECTED_URL, 0)

        loadImagen(selectedPos)




    }

 // Cargo la imagen de la lista  -------------------------------------------------------------------

    fun loadImagen(itemPos: Int) {
        Picasso.get().load(imagenesList[itemPos]).into(binding.itemImage)
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
}