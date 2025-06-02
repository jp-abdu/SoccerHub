package com.example.sportsmc

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import androidx.viewpager2.widget.ViewPager2
import retrofit2.*
import retrofit2.converter.gson.GsonConverterFactory

// Activity principal do app, responsável por exibir as abas e gerenciar ações do usuário
class MainActivity : AppCompatActivity() {
    // Títulos das abas exibidas no TabLayout
    private val tabTitles = arrayOf("Resultados", "Artilheiros", "Tabela")

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Inicializa o ViewPager2 e o TabLayout
        val viewPager = findViewById<ViewPager2>(R.id.viewPager)
        val tabLayout = findViewById<TabLayout>(R.id.tabLayout)

        // Define o adapter do ViewPager2 para gerenciar os fragments das abas
        viewPager.adapter = MainPagerAdapter(this)

        // Conecta o TabLayout ao ViewPager2 e define os títulos das abas
        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = tabTitles[position]
        }.attach()

        // Ação do botão "Meu Time": abre detalhes do time do coração do usuário
        findViewById<Button>(R.id.btnMeuTime)?.setOnClickListener {
            val prefs = getSharedPreferences("prefs", MODE_PRIVATE)
            var idTime = prefs.getInt("ID_TIME_CORACAO", -1)
            if (idTime != -1) {
                // Se já tem o ID salvo, abre a tela de detalhes do time
                val intent = Intent(this, TeamDetailActivity::class.java)
                intent.putExtra("TEAM_ID", idTime)
                startActivity(intent)
            } else {
                // Se não tem o ID, tenta buscar pelo nome salvo nas preferências
                val nomeTime = prefs.getString("NOME_TIME_CORACAO", null)
                if (nomeTime.isNullOrBlank()) {
                    Toast.makeText(this, "Seu time do coração não foi cadastrado.", Toast.LENGTH_SHORT).show()
                } else {
                    // Busca o ID do time pelo nome usando a API
                    buscarIdTimePorNome(nomeTime) { idEncontrado ->
                        if (idEncontrado != null) {
                            prefs.edit().putInt("ID_TIME_CORACAO", idEncontrado).apply()
                            val intent = Intent(this, TeamDetailActivity::class.java)
                            intent.putExtra("TEAM_ID", idEncontrado)
                            startActivity(intent)
                        } else {
                            Toast.makeText(this, "Time não encontrado.", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
        }

        // Ação do botão "Sair": limpa dados do usuário e volta para a tela inicial
        findViewById<Button>(R.id.btnSair).setOnClickListener {
            getSharedPreferences("prefs", MODE_PRIVATE).edit().clear().apply()
            val intent = Intent(this, SplashActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
            finish()
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