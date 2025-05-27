package com.example.sportsmc

import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import retrofit2.*
import retrofit2.converter.gson.GsonConverterFactory

class TeamDetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_team_detail)

        val teamId = intent.getIntExtra("TEAM_ID", -1)
        val imgCrest = findViewById<ImageView>(R.id.imgTeamCrest)
        val txtName = findViewById<TextView>(R.id.txtTeamName)
        val txtDetails = findViewById<TextView>(R.id.txtTeamDetails)
        val layoutSquad = findViewById<LinearLayout>(R.id.layoutSquad)
        val btnVoltar = findViewById<Button>(R.id.btnVoltar)

        btnVoltar.setOnClickListener { finish() }

        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.football-data.org/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val api = retrofit.create(FootballDataApi::class.java)
        val apiKey = "ca9bd78222a44a44bffca0ebbbdf2d00"

        api.getTeam(teamId, apiKey).enqueue(object : Callback<TeamDetailResponse> {
            override fun onResponse(call: Call<TeamDetailResponse>, response: Response<TeamDetailResponse>) {
                if (response.isSuccessful) {
                    val team = response.body()
                    txtName.text = team?.name ?: "-"
                    Glide.with(this@TeamDetailActivity)
                        .load(team?.crest)
                        .placeholder(R.drawable.ic_launcher_background)
                        .into(imgCrest)

                    val detalhes = buildString {
                        append("Fundação: ${team?.founded ?: "-"}\n")
                        append("Estádio: ${team?.venue ?: "-"}\n")
                        append("Cidade: ${team?.address ?: "-"}\n")
                        append("Site: ${team?.website ?: "-"}")
                    }
                    txtDetails.text = detalhes

                    // Mapeamento das posições para português (apenas 4 grupos)
                    val posicoesPt = mapOf(
                        "Goalkeeper" to "Goleiros",
                        "Defence" to "Defensores",
                        "Defender" to "Defensores",
                        "Centre-Back" to "Defensores",
                        "Full-Back" to "Defensores",
                        "Midfield" to "Meias",
                        "Midfielder" to "Meias",
                        "Offence" to "Atacantes",
                        "Attacker" to "Atacantes"
                    )
                    // Ordem de exibição
                    val positionsOrder = listOf("Goalkeeper", "Defender", "Midfielder", "Attacker")

                    layoutSquad.removeAllViews()
                    val elenco = team?.squad ?: emptyList()
                    val posicoesUsadas = mutableSetOf<String>()

                    // Exibe as posições principais na ordem desejada
                    positionsOrder.forEach { pos ->
                        val jogadores = when (pos) {
                            "Defender" -> elenco.filter { it.position == "Defender" || it.position == "Centre-Back" || it.position == "Full-Back" || it.position == "Defence" }
                            "Midfielder" -> elenco.filter { it.position == "Midfielder" || it.position == "Midfield" }
                            "Attacker" -> elenco.filter { it.position == "Attacker" || it.position == "Offence" }
                            else -> elenco.filter { it.position == pos }
                        }
                        if (jogadores.isNotEmpty()) {
                            val titulo = TextView(this@TeamDetailActivity)
                            titulo.text = posicoesPt[pos] ?: pos
                            titulo.textSize = 18f
                            titulo.setTextColor(getColor(R.color.cor_primaria))
                            titulo.setPadding(0, 12, 0, 4)
                            layoutSquad.addView(titulo)
                            jogadores.forEach { player ->
                                val tv = TextView(this@TeamDetailActivity)
                                tv.text = player.name
                                tv.textSize = 16f
                                tv.setTextColor(getColor(R.color.black))
                                tv.setPadding(16, 2, 0, 2)
                                layoutSquad.addView(tv)
                            }
                        }
                    }

                    // Jogadores sem posição
                    val semPos = elenco.filter { it.position == null }
                    if (semPos.isNotEmpty()) {
                        val titulo = TextView(this@TeamDetailActivity)
                        titulo.text = "Sem posição"
                        titulo.textSize = 18f
                        titulo.setTextColor(getColor(R.color.cor_primaria))
                        titulo.setPadding(0, 12, 0, 4)
                        layoutSquad.addView(titulo)
                        semPos.forEach { player ->
                            val tv = TextView(this@TeamDetailActivity)
                            tv.text = player.name
                            tv.textSize = 16f
                            tv.setTextColor(getColor(R.color.black))
                            tv.setPadding(16, 2, 0, 2)
                            layoutSquad.addView(tv)
                        }
                    }
                } else {
                    Toast.makeText(this@TeamDetailActivity, "Erro ao carregar time", Toast.LENGTH_SHORT).show()
                }
            }
            override fun onFailure(call: Call<TeamDetailResponse>, t: Throwable) {
                Toast.makeText(this@TeamDetailActivity, "Falha na conexão", Toast.LENGTH_SHORT).show()
            }
        })
    }
}

