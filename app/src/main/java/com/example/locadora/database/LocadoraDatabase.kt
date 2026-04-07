package com.example.locadora.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.locadora.model.Carro
import com.example.locadora.model.Locacao
import com.example.locadora.model.Usuario
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(entities = [Usuario::class, Carro::class, Locacao::class], version = 1)
abstract class LocadoraDatabase : RoomDatabase() {

    abstract fun usuarioDao(): UsuarioDao
    abstract fun carroDao(): CarroDao
    abstract fun locacaoDao(): LocacaoDao

    companion object {
        @Volatile
        private var INSTANCE: LocadoraDatabase? = null

        fun getDatabase(context: Context, scope: CoroutineScope): LocadoraDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    LocadoraDatabase::class.java,
                    "locadora_database"
                )
                .addCallback(LocadoraDatabaseCallback(scope))
                .build()
                INSTANCE = instance
                instance
            }
        }
    }

    private class LocadoraDatabaseCallback(
        private val scope: CoroutineScope
    ) : RoomDatabase.Callback() {

        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            INSTANCE?.let { database ->
                scope.launch(Dispatchers.IO) {
                    populateDatabase(database.carroDao())
                }
            }
        }

        suspend fun populateDatabase(carroDao: CarroDao) {
            carroDao.insert(Carro(modelo = "Onix", marca = "Chevrolet", ano = 2023, placa = "ABC-1234", tipo = "Popular", precoDiaria = 120.0))
            carroDao.insert(Carro(modelo = "Corolla", marca = "Toyota", ano = 2024, placa = "XYZ-9876", tipo = "Sedan", precoDiaria = 250.0))
            carroDao.insert(Carro(modelo = "Compass", marca = "Jeep", ano = 2023, placa = "JEP-5555", tipo = "SUV", precoDiaria = 350.0))
            carroDao.insert(Carro(modelo = "Hilux", marca = "Toyota", ano = 2024, placa = "HLX-4444", tipo = "Caminhonete", precoDiaria = 450.0))
            carroDao.insert(Carro(modelo = "HB20", marca = "Hyundai", ano = 2022, placa = "HBD-2020", tipo = "Popular", precoDiaria = 110.0))
        }
    }
}
