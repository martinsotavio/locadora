package com.example.locadora.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.locadora.databinding.ItemCarroBinding
import com.example.locadora.model.Carro

class CarroAdapter(
    private var carros: List<Carro>,
    private val onCarClick: (Carro) -> Unit
) : RecyclerView.Adapter<CarroAdapter.CarroViewHolder>() {

    fun updateList(newList: List<Carro>) {
        carros = newList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CarroViewHolder {
        val binding = ItemCarroBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CarroViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CarroViewHolder, position: Int) {
        val carro = carros[position]
        holder.bind(carro)
    }

    override fun getItemCount() = carros.size

    inner class CarroViewHolder(private val binding: ItemCarroBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(carro: Carro) {
            val context = binding.root.context
            binding.tvModelo.text = carro.modelo
            binding.tvMarcaAno.text = "${carro.marca} - ${carro.ano}"
            binding.tvTipo.text = carro.tipo
            binding.tvPreco.text = context.getString(com.example.locadora.R.string.daily_rate, carro.precoDiaria)
            
            binding.root.setOnClickListener { onCarClick(carro) }
        }
    }
}
