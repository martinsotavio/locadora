package com.example.locadora.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "locacoes")
data class Locacao(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val carroId: Int,
    val usuarioId: Int,
    val dataInicio: String,
    val dataFim: String,
    val valorTotal: Double
)
