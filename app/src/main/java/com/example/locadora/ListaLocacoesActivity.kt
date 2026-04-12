package com.example.locadora

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.locadora.adapter.LocacaoAdapter
import com.example.locadora.database.LocadoraDatabase
import com.example.locadora.databinding.ActivityListaLocacoesBinding
import kotlinx.coroutines.launch

class ListaLocacoesActivity : AppCompatActivity() {

    private lateinit var binding: ActivityListaLocacoesBinding
    private lateinit var adapter: LocacaoAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListaLocacoesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupToolbar()
        setupRecyclerView()
        loadLocacoes()
    }

    private fun setupToolbar() {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Relatório de Aluguéis"
    }

    private fun setupRecyclerView() {
        adapter = LocacaoAdapter(emptyList())
        binding.rvLocacoes.adapter = adapter
        binding.rvLocacoes.layoutManager = LinearLayoutManager(this)
    }

    private fun loadLocacoes() {
        val db = LocadoraDatabase.getDatabase(this)
        lifecycleScope.launch {
            val locacoes = db.locacaoDao().getAllLocacoesComDetalhes()
            adapter.updateList(locacoes)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}
