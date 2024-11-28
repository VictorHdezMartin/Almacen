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
        const val EXTRA_ADDRESS_URL = "EXTRA_ADDRESS_URL"
    }

    lateinit var binding: ActivityItemDetailImagenBinding

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

        var url = intent.getStringExtra(EXTRA_ADDRESS_URL)!!
        Picasso.get().load(url).into(binding.itemImage)

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