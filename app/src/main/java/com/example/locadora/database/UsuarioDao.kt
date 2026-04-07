package com.example.locadora.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.locadora.model.Usuario

@Dao
interface UsuarioDao {
    @Insert
    suspend fun insert(usuario: Usuario)

    @Query("SELECT * FROM usuarios WHERE email = :email AND senha = :senha LIMIT 1")
    suspend fun login(email: String, senha: String): Usuario?

    @Query("SELECT * FROM usuarios WHERE email = :email LIMIT 1")
    suspend fun getUserByEmail(email: String): Usuario?
}
