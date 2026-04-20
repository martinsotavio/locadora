package com.example.locadora

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.locadora.database.LocadoraDatabase
import com.example.locadora.databinding.ActivityDetalhesCarroBinding
import com.example.locadora.model.Carro
import com.example.locadora.model.Locacao
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import java.util.concurrent.TimeUnit

class DetalhesCarroActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetalhesCarroBinding
    private var carro: Carro? = null
    private var valorTotalCalculado: Double = 0.0
    private val calendar = Calendar.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetalhesCarroBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Detalhes do Aluguel"

        binding.toolbar.setNavigationOnClickListener {
            onBackPressed()
        }

        val carroId = intent.getIntExtra("carro_id", -1)
        val db = LocadoraDatabase.getDatabase(this)

        lifecycleScope.launch {
            carro = db.carroDao().getCarroById(carroId)
            carro?.let {
                binding.tvDetalheModelo.text = it.modelo
                binding.tvDetalheDescricao.text = "${it.marca} | ${it.ano} | ${it.placa} | ${it.tipo}"
            }
        }

        binding.etDataInicio.setOnClickListener {
            showDatePicker { date -> binding.etDataInicio.setText(date) }
        }

        binding.etDataFim.setOnClickListener {
            showDatePicker { date -> binding.etDataFim.setText(date) }
        }

        binding.btnCalcular.setOnClickListener {
            calcularValor()
        }

        binding.btnAlugar.setOnClickListener {
            realizarLocacao(db)
        }
    }

    private fun showDatePicker(onDateSelected: (String) -> Unit) {
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        DatePickerDialog(this, { _, selectedYear, selectedMonth, selectedDay ->
            val formattedDate = "%02d/%02d/%04d".format(selectedDay, selectedMonth + 1, selectedYear)
            onDateSelected(formattedDate)
        }, year, month, day).show()
    }

    private fun calcularValor() {
        val dataInicioStr = binding.etDataInicio.text.toString()
        val dataFimStr = binding.etDataFim.text.toString()

        if (dataInicioStr.isEmpty() || dataFimStr.isEmpty()) {
            Toast.makeText(this, "Informe as datas", Toast.LENGTH_SHORT).show()
            return
        }

        try {
            val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
            val dataInicio = sdf.parse(dataInicioStr)
            val dataFim = sdf.parse(dataFimStr)

            if (dataInicio != null && dataFim != null) {
                val diffInMillis = dataFim.time - dataInicio.time
                val dias = TimeUnit.MILLISECONDS.toDays(diffInMillis)

                if (dias <= 0) {
                    Toast.makeText(this, "A data fim deve ser após a data início", Toast.LENGTH_SHORT).show()
                    return
                }

                carro?.let {
                    val taxaLocadora = 50.0
                    valorTotalCalculado = (dias * it.precoDiaria) + taxaLocadora
                    binding.tvValorTotal.text = "Valor Total: R$ %.2f\n($dias dias + R$ 50,00 taxa)".format(valorTotalCalculado)
                    binding.btnAlugar.visibility = View.VISIBLE
                }
            }
        } catch (e: Exception) {
            Toast.makeText(this, "Formato de data inválido (DD/MM/AAAA)", Toast.LENGTH_SHORT).show()
        }
    }

    private fun realizarLocacao(db: LocadoraDatabase) {
        val userId = getSharedPreferences("user_prefs", MODE_PRIVATE).getInt("user_id", -1)
        
        if (userId == -1) {
            Toast.makeText(this, "Erro ao identificar usuário. Faça login novamente.", Toast.LENGTH_SHORT).show()
            return
        }

        lifecycleScope.launch {
            val sdfRegistro = SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.getDefault())
            val dataRegistro = sdfRegistro.format(Calendar.getInstance().time)

            val locacao = Locacao(
                carroId = carro?.id ?: 0,
                usuarioId = userId,
                dataInicio = binding.etDataInicio.text.toString(),
                dataFim = binding.etDataFim.text.toString(),
                valorTotal = valorTotalCalculado,
                dataRegistro = dataRegistro
            )
            db.locacaoDao().insert(locacao)
            Toast.makeText(this@DetalhesCarroActivity, "Locação realizada com sucesso!", Toast.LENGTH_LONG).show()
            

            android.content.Intent(this@DetalhesCarroActivity, ListaLocacoesActivity::class.java).also {
                startActivity(it)
                finish()
            }
        }
    }
}
