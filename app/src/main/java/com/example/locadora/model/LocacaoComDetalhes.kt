package com.example.locadora.model

data class LocacaoComDetalhes(
    val id: Int,
    val modeloCarro: String,
    val nomeUsuario: String,
    val dataInicio: String,
    val dataFim: String,
    val valorTotal: Double,
    val dataRegistro: String
)
