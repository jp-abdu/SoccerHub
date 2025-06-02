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

// Fragmento responsável por exibir a lista de artilheiros
class ArtilheirosFragment : Fragment() {
    // Cria a view do fragmento
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Infla o layout do fragmento
        val view = inflater.inflate(R.layout.fragment_artilheiros, container, false)
        // Obtém o RecyclerView e define o layout como lista vertical
        val recycler = view.findViewById<RecyclerView>(R.id.recyclerArtilheiros)
        recycler.layoutManager = LinearLayoutManager(context)

        // Configura o Retrofit para consumir a API de futebol
        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.football-data.org/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val api = retrofit.create(FootballDataApi::class.java)
        val apiKey = "ca9bd78222a44a44bffca0ebbbdf2d00"

        // Faz a requisição para buscar os artilheiros
        api.listarArtilheiros("BSA", apiKey).enqueue(object : Callback<ScorersResponse> {
            // Se a resposta for bem-sucedida, exibe os artilheiros no RecyclerView
            override fun onResponse(call: Call<ScorersResponse>, response: Response<ScorersResponse>) {
                if (response.isSuccessful) {
                    val artilheiros = response.body()?.scorers ?: emptyList()
                    recycler.adapter = ArtilheirosAdapter(artilheiros)
                } else {
                    Toast.makeText(context, "Erro ao carregar artilheiros", Toast.LENGTH_SHORT).show()
                }
            }
            // Se houver falha na conexão, exibe mensagem de erro
            override fun onFailure(call: Call<ScorersResponse>, t: Throwable) {
                Toast.makeText(context, "Falha na conexão", Toast.LENGTH_SHORT).show()
            }
        })
        return view
    }
}