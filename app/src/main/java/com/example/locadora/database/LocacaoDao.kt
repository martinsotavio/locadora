package com.example.locadora.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.locadora.model.Locacao
import com.example.locadora.model.LocacaoComDetalhes

@Dao
interface LocacaoDao {
    @Insert
    suspend fun insert(locacao: Locacao)

    @Query("""
        SELECT l.id, c.modelo as modeloCarro, u.nomeCompleto as nomeUsuario, 
               l.dataInicio, l.dataFim, l.valorTotal, l.dataRegistro
        FROM locacoes l
        INNER JOIN carros c ON l.carroId = c.id
        INNER JOIN usuarios u ON l.usuarioId = u.id
        ORDER BY l.id DESC
    """)
    suspend fun getAllLocacoesComDetalhes(): List<LocacaoComDetalhes>

    @Query("SELECT * FROM locacoes WHERE usuarioId = :usuarioId")
    suspend fun getLocacoesByUsuario(usuarioId: Int): List<Locacao>
}
