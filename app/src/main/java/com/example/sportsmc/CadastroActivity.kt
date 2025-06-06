package com.example.sportsmc

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import retrofit2.*
import retrofit2.converter.gson.GsonConverterFactory

// Activity responsável pelo cadastro do usuário
class CadastroActivity : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cadastro)

        // Define ação do botão de envio do cadastro
        findViewById<Button>(R.id.btnEnviarCadastro).setOnClickListener {
            // Obtém os valores dos campos de texto
            val nome = findViewById<EditText>(R.id.edtNome).text.toString()
            val idade = findViewById<EditText>(R.id.edtIdade).text.toString()
            val email = findViewById<EditText>(R.id.edtEmail).text.toString()
            val nomeTime = findViewById<EditText>(R.id.edtTime).text.toString()

            // Validação: nome só pode ter letras
            if (!nome.matches(Regex("^[A-Za-zÀ-ÿ\\s]+$"))) {
                Toast.makeText(this, "Nome deve conter apenas letras.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            // Validação: idade só pode ter números
            if (!idade.matches(Regex("^\\d+$"))) {
                Toast.makeText(this, "Idade deve conter apenas números.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            // Validação: idade deve ser no máximo 120 anos
            if (idade.toInt() > 120) {
                Toast.makeText(this, "Idade deve ser no máximo 120 anos.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            // Validação: email deve ser válido
            if (!email.matches(Regex("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$"))) {
                Toast.makeText(this, "Email inválido.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            // Validação: nome do time não pode ser vazio
            if (nomeTime.isBlank()) {
                Toast.makeText(this, "Informe o time do coração", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Busca o ID do time pelo nome usando a API
            buscarIdTimePorNome(nomeTime) { idTime ->
                if (idTime != null) {
                    // Salva o ID e nome do time nas preferências
                    getSharedPreferences("prefs", MODE_PRIVATE)
                        .edit()
                        .putInt("ID_TIME_CORACAO", idTime)
                        .putString("NOME_TIME_CORACAO", nomeTime)
                        .apply()

                    // Vai para a tela de confirmação, passando o nome do usuário
                    val intent = Intent(this, ConfirmacaoActivity::class.java)
                    intent.putExtra("NOME_USUARIO", nome)
                    startActivity(intent)
                    finish()
                } else {
                    // Mostra erro se o time não for encontrado
                    Toast.makeText(this, "Time não encontrado. Verifique o nome.", Toast.LENGTH_SHORT).show()
                }
            }
        }

        // Define ação do botão "Fale Conosco"
        findViewById<Button>(R.id.btnFaleConosco).setOnClickListener {
            val intent = Intent(Intent.ACTION_SENDTO).apply {
                data = Uri.parse("mailto:suporte@sportsmc.com")
                putExtra(Intent.EXTRA_SUBJECT, "Dúvida ou Sugestão")
            }
            startActivity(intent)
        }
    }

    // Função para buscar o ID do time pelo nome usando Retrofit
    private fun buscarIdTimePorNome(nome: String, callback: (Int?) -> Unit) {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.football-data.org/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val api = retrofit.create(FootballDataApi::class.java)
        val apiKey = "ca9bd78222a44a44bffca0ebbbdf2d00"
        // Faz a requisição para obter a tabela e encontrar o time
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

