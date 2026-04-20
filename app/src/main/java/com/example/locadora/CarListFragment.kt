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
            var listaCarros = db.carroDao().getAllCarros()
            

            if (listaCarros.isEmpty()) {
                db.carroDao().insert(com.example.locadora.model.Carro(modelo = "Civic", marca = "Honda", ano = 2024, placa = "CIV-2024", tipo = "Sedan", precoDiaria = 280.0))
                db.carroDao().insert(com.example.locadora.model.Carro(modelo = "Renegade", marca = "Jeep", ano = 2023, placa = "RNG-8888", tipo = "SUV", precoDiaria = 220.0))
                db.carroDao().insert(com.example.locadora.model.Carro(modelo = "Onix", marca = "Chevrolet", ano = 2023, placa = "ABC-1234", tipo = "Popular", precoDiaria = 120.0))
                db.carroDao().insert(com.example.locadora.model.Carro(modelo = "Corolla", marca = "Toyota", ano = 2024, placa = "XYZ-9876", tipo = "Sedan", precoDiaria = 250.0))
                db.carroDao().insert(com.example.locadora.model.Carro(modelo = "Compass", marca = "Jeep", ano = 2023, placa = "JEP-5555", tipo = "SUV", precoDiaria = 350.0))
                db.carroDao().insert(com.example.locadora.model.Carro(modelo = "Hilux", marca = "Toyota", ano = 2024, placa = "HLX-4444", tipo = "Caminhonete", precoDiaria = 450.0))
                db.carroDao().insert(com.example.locadora.model.Carro(modelo = "HB20", marca = "Hyundai", ano = 2022, placa = "HBD-2020", tipo = "Popular", precoDiaria = 110.0))
                listaCarros = db.carroDao().getAllCarros()
            }

            adapter.updateList(listaCarros)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
