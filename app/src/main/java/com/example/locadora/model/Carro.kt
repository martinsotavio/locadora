package com.example.locadora.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "carros")
data class Carro(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val modelo: String,
    val marca: String,
    val ano: Int,
    val placa: String,
    val tipo: String,
    val precoDiaria: Double,
    val disponivel: Boolean = true
)
