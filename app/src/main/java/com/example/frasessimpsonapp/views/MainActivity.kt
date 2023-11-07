package com.example.frasessimpsonapp.views

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.frasessimpsonapp.R
import com.example.frasessimpsonapp.databinding.ActivityMainBinding
import com.example.frasessimpsonapp.viewmodels.MainViewModel
import com.example.frasessimpsonapp.views.adapters.PersonajeAdapter

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainViewModel
    private lateinit var adapter: PersonajeAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        viewModel = ViewModelProvider(this)[MainViewModel::class.java]

        setupRecyclerView()

        viewModel.obtenerPersonajes()

        viewModel.listaPersonajes.observe(this) {
            adapter.listaPersonajes = it
            adapter.notifyDataSetChanged()
        }

        binding.tilBuscar.setEndIconOnClickListener {
            ocultarTeclado()
            if (binding.tietBuscar.text.toString() == "") {
                viewModel.obtenerPersonajes()
            } else {
                viewModel.obtenerPersonaje(binding.tietBuscar.text.toString().trim())
            }
        }
    }

    private fun setupRecyclerView() {
        binding.rvPersonajes.layoutManager = GridLayoutManager(this, 3)
        adapter = PersonajeAdapter(this, arrayListOf())
        binding.rvPersonajes.adapter = adapter
    }

    private fun ocultarTeclado() {
        val view = this.currentFocus
        if (view != null) {
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }


    }
}