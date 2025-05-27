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

class MainActivity : AppCompatActivity() {
    private val tabTitles = arrayOf("Resultados", "Artilheiros", "Tabela")

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val viewPager = findViewById<ViewPager2>(R.id.viewPager)
        val tabLayout = findViewById<TabLayout>(R.id.tabLayout)

        viewPager.adapter = MainPagerAdapter(this)

        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = tabTitles[position]
        }.attach()

        findViewById<Button>(R.id.btnMeuTime)?.setOnClickListener {
            val prefs = getSharedPreferences("prefs", MODE_PRIVATE)
            var idTime = prefs.getInt("ID_TIME_CORACAO", -1)
            if (idTime != -1) {
                val intent = Intent(this, TeamDetailActivity::class.java)
                intent.putExtra("TEAM_ID", idTime)
                startActivity(intent)
            } else {
                val nomeTime = prefs.getString("NOME_TIME_CORACAO", null)
                if (nomeTime.isNullOrBlank()) {
                    Toast.makeText(this, "Seu time do coração não foi cadastrado.", Toast.LENGTH_SHORT).show()
                } else {
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