package com.example.locadora

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.locadora.database.LocadoraDatabase
import com.example.locadora.databinding.ActivityRegisterBinding
import com.example.locadora.model.Usuario
import kotlinx.coroutines.launch

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Cadastro"

        binding.toolbar.setNavigationOnClickListener {
            onBackPressed()
        }
        binding.tvLogin.setOnClickListener {
            finish()
        }
        val db = LocadoraDatabase.getDatabase(this)

        binding.btnRegister.setOnClickListener {
            val name = binding.etFullName.text.toString()
            val email = binding.etEmail.text.toString()
            val password = binding.etPassword.text.toString()

            if (name.isNotEmpty() && email.isNotEmpty() && password.isNotEmpty()) {
                lifecycleScope.launch {
                    val existingUser = db.usuarioDao().getUserByEmail(email)
                    if (existingUser == null) {
                        db.usuarioDao().insert(Usuario(nomeCompleto = name, email = email, senha = password))
                        Toast.makeText(this@RegisterActivity, "Cadastro realizado!", Toast.LENGTH_SHORT).show()
                        finish()
                    } else {
                        Toast.makeText(this@RegisterActivity, "E-mail já cadastrado", Toast.LENGTH_SHORT).show()
                    }
                }
            } else {
                Toast.makeText(this, "Preencha todos os campos", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
