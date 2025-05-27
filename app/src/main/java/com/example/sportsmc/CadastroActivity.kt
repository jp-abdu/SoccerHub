package com.example.sportsmc

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import retrofit2.*
import retrofit2.converter.gson.GsonConverterFactory

class CadastroActivity : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cadastro)

        findViewById<Button>(R.id.btnEnviarCadastro).setOnClickListener {
            val nomeTime = findViewById<EditText>(R.id.edtTime).text.toString()
            if (nomeTime.isBlank()) {
                Toast.makeText(this, "Informe o time do coração", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            buscarIdTimePorNome(nomeTime) { idTime ->
                if (idTime != null) {
                    getSharedPreferences("prefs", MODE_PRIVATE)
                        .edit()
                        .putInt("ID_TIME_CORACAO", idTime)
                        .putString("NOME_TIME_CORACAO", nomeTime)
                        .apply()
                } else {
                    Toast.makeText(this, "Time não encontrado. Verifique o nome.", Toast.LENGTH_SHORT).show()
                }
                startActivity(Intent(this, ConfirmacaoActivity::class.java))
                finish()
            }
        }

        findViewById<Button>(R.id.btnFaleConosco).setOnClickListener {
            val intent = Intent(Intent.ACTION_SENDTO).apply {
                data = Uri.parse("mailto:suporte@sportsmc.com")
                putExtra(Intent.EXTRA_SUBJECT, "Dúvida ou Sugestão")
            }
            startActivity(intent)
        }
    }

    private fun buscarIdTimePorNome(nome: String, callback: (Int?) -> Unit) {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.football-data.org/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val api = retrofit.create(FootballDataApi::class.java)
        val apiKey = "ca9bd78222a44a44bffca0ebbbdf2d00"
        api.listarTabela("BSA", apiKey).enqueue(object : Callback<StandingsResponse> {
            override fun onResponse(call: Call<StandingsResponse>, response: Response<StandingsResponse>) {
                val tabela = response.body()?.standings?.firstOrNull()?.table ?: emptyList()
                val time = tabela.find { it.team.name.equals(nome, ignoreCase = true) }
                callback(time?.team?.id)
            }
            override fun onFailure(call: Call<StandingsResponse>, t: Throwable) {
                callback(null)
            }
        })
    }
}