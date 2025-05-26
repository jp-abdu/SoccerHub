package com.example.sportsmc

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import retrofit2.*
import retrofit2.converter.gson.GsonConverterFactory

class ResultadosFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_resultados, container, false)
        val recycler = view.findViewById<RecyclerView>(R.id.recyclerResultados)
        recycler.layoutManager = LinearLayoutManager(context)

        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.football-data.org/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val api = retrofit.create(FootballDataApi::class.java)
        val apiKey = "ca9bd78222a44a44bffca0ebbbdf2d00"

        api.listarPartidas("BSA", apiKey).enqueue(object : Callback<MatchResponse> {

            override fun onResponse(call: Call<MatchResponse>, response: Response<MatchResponse>) {
                if (response.isSuccessful) {
                    val partidas = response.body()?.matches ?: emptyList()
                    val partidasOrdenadas = partidas
                        .filter { it.score.fullTime.home != null && it.score.fullTime.away != null }
                        .sortedByDescending { it.matchday ?: 0 } +
                            partidas.filter { it.score.fullTime.home == null || it.score.fullTime.away == null }
                                .sortedBy { it.matchday ?: 0 }

                    recycler.adapter = PartidasAdapter(partidasOrdenadas)
                } else {
                    Toast.makeText(context, "Erro ao carregar resultados", Toast.LENGTH_SHORT).show()
                }
            }
            override fun onFailure(call: Call<MatchResponse>, t: Throwable) {
                Toast.makeText(context, "Falha na conexão", Toast.LENGTH_SHORT).show()
            }
        })
        return view
    }
}