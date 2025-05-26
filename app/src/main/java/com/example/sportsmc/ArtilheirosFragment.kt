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

class ArtilheirosFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_artilheiros, container, false)
        val recycler = view.findViewById<RecyclerView>(R.id.recyclerArtilheiros)
        recycler.layoutManager = LinearLayoutManager(context)

        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.football-data.org/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val api = retrofit.create(FootballDataApi::class.java)
        val apiKey = "ca9bd78222a44a44bffca0ebbbdf2d00"

        api.listarArtilheiros("BSA", apiKey).enqueue(object : Callback<ScorersResponse> {
            override fun onResponse(call: Call<ScorersResponse>, response: Response<ScorersResponse>) {
                if (response.isSuccessful) {
                    val artilheiros = response.body()?.scorers ?: emptyList()
                    recycler.adapter = ArtilheirosAdapter(artilheiros)
                } else {
                    Toast.makeText(context, "Erro ao carregar artilheiros", Toast.LENGTH_SHORT).show()
                }
            }
            override fun onFailure(call: Call<ScorersResponse>, t: Throwable) {
                Toast.makeText(context, "Falha na conexão", Toast.LENGTH_SHORT).show()
            }
        })
        return view
    }
}