package com.example.locadora.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.locadora.databinding.ItemLocacaoBinding
import com.example.locadora.model.LocacaoComDetalhes

class LocacaoAdapter(
    private var locacoes: List<LocacaoComDetalhes>
) : RecyclerView.Adapter<LocacaoAdapter.LocacaoViewHolder>() {

    fun updateList(newList: List<LocacaoComDetalhes>) {
        locacoes = newList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LocacaoViewHolder {
        val binding = ItemLocacaoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return LocacaoViewHolder(binding)
    }

    override fun onBindViewHolder(holder: LocacaoViewHolder, position: Int) {
        holder.bind(locacoes[position])
    }

    override fun getItemCount() = locacoes.size

    inner class LocacaoViewHolder(private val binding: ItemLocacaoBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(locacao: LocacaoComDetalhes) {
            binding.tvModeloCarro.text = locacao.modeloCarro
            binding.tvNomeUsuario.text = "Locatário: ${locacao.nomeUsuario}"
            binding.tvPeriodo.text = "Período: ${locacao.dataInicio} - ${locacao.dataFim}"
            binding.tvValorTotal.text = String.format("Total: R$ %.2f", locacao.valorTotal)
            
            // Exibindo o horário do registro da locação
            binding.tvDataRegistro.text = "Realizado em: ${locacao.dataRegistro}"
            
            binding.root.contentDescription = "${locacao.modeloCarro} alugado por ${locacao.nomeUsuario}. Realizado em: ${locacao.dataRegistro}"
        }
    }
}
