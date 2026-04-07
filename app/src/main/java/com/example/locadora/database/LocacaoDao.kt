package com.example.locadora.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.locadora.model.Locacao

@Dao
interface LocacaoDao {
    @Insert
    suspend fun insert(locacao: Locacao)

    @Query("SELECT * FROM locacoes WHERE usuarioId = :usuarioId")
    suspend fun getLocacoesByUsuario(usuarioId: Int): List<Locacao>
}
