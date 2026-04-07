package com.example.locadora.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.locadora.model.Carro

@Dao
interface CarroDao {
    @Query("SELECT * FROM carros")
    suspend fun getAllCarros(): List<Carro>

    @Query("SELECT * FROM carros WHERE id = :id")
    suspend fun getCarroById(id: Int): Carro?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(carro: Carro)

    @Update
    suspend fun update(carro: Carro)
}
