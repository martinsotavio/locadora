package com.example.locadora

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.locadora.adapter.CarroAdapter
import com.example.locadora.database.LocadoraDatabase
import com.example.locadora.databinding.FragmentCarListBinding
import kotlinx.coroutines.launch

class CarListFragment : Fragment() {

    private var _binding: FragmentCarListBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: CarroAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCarListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        loadCars()
    }

    private fun setupRecyclerView() {
        adapter = CarroAdapter(emptyList()) { carro ->
            val intent = Intent(requireContext(), DetalhesCarroActivity::class.java)
            intent.putExtra("carro_id", carro.id)
            startActivity(intent)
        }
        binding.rvCars.layoutManager = LinearLayoutManager(requireContext())
        binding.rvCars.adapter = adapter
    }

    private fun loadCars() {
        val db = LocadoraDatabase.getDatabase(requireContext())
        viewLifecycleOwner.lifecycleScope.launch {
            val listaCarros = db.carroDao().getAllCarros()
            adapter.updateList(listaCarros)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
