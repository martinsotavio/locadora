package com.example.locadora

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.locadora.adapter.CarroAdapter
import com.example.locadora.database.LocadoraDatabase
import com.example.locadora.databinding.ActivityMainBinding
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: CarroAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val db = LocadoraDatabase.getDatabase(this, lifecycleScope)

        setupRecyclerView()

        lifecycleScope.launch {
            val listaCarros = db.carroDao().getAllCarros()
            adapter.updateList(listaCarros)
        }
    }

    private fun setupRecyclerView() {
        adapter = CarroAdapter(emptyList()) { carro ->
            val intent = Intent(this, DetalhesCarroActivity::class.java)
            intent.putExtra("carro_id", carro.id)
            startActivity(intent)
        }
        binding.rvCars.layoutManager = LinearLayoutManager(this)
        binding.rvCars.adapter = adapter
    }
}
