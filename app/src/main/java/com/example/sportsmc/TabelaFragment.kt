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

// Fragment responsável por exibir a tabela de classificação dos times
class TabelaFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Infla o layout do fragmento
        val view = inflater.inflate(R.layout.fragment_tabela, container, false)
        // Configura o RecyclerView para exibir a tabela
        val recycler = view.findViewById<RecyclerView>(R.id.recyclerTabela)
        recycler.layoutManager = LinearLayoutManager(context)

        // Inicializa Retrofit para consumir a API de futebol
        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.football-data.org/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val api = retrofit.create(FootballDataApi::class.java)
        val apiKey = "ca9bd78222a44a44bffca0ebbbdf2d00"

        // Faz a requisição para obter a tabela de classificação
        api.listarTabela("BSA", apiKey).enqueue(object : Callback<StandingsResponse> {
            override fun onResponse(call: Call<StandingsResponse>, response: Response<StandingsResponse>) {
                if (response.isSuccessful) {
                    // Obtém a lista de times da tabela (primeira classificação retornada)
                    val tabela = response.body()?.standings?.firstOrNull()?.table ?: emptyList()
                    // Define o adapter do RecyclerView para exibir a tabela
                    recycler.adapter = TabelaAdapter(tabela)
                } else {
                    // Exibe mensagem de erro se a resposta não for bem-sucedida
                    Toast.makeText(context, "Erro ao carregar tabela", Toast.LENGTH_SHORT).show()
                }
            }
            override fun onFailure(call: Call<StandingsResponse>, t: Throwable) {
                // Exibe mensagem de erro em caso de falha na conexão
                Toast.makeText(context, "Falha na conexão", Toast.LENGTH_SHORT).show()
            }
        })
        return view
    }
}