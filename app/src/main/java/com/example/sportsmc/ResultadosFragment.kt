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

// Fragment responsável por exibir a lista de resultados das partidas
class ResultadosFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Infla o layout do fragmento
        val view = inflater.inflate(R.layout.fragment_resultados, container, false)
        // Configura o RecyclerView para exibir a lista de partidas
        val recycler = view.findViewById<RecyclerView>(R.id.recyclerResultados)
        recycler.layoutManager = LinearLayoutManager(context)

        // Inicializa Retrofit para consumir a API de futebol
        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.football-data.org/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val api = retrofit.create(FootballDataApi::class.java)
        val apiKey = "ca9bd78222a44a44bffca0ebbbdf2d00"

        // Faz a requisição para obter as partidas
        api.listarPartidas("BSA", apiKey).enqueue(object : Callback<MatchResponse> {
            override fun onResponse(call: Call<MatchResponse>, response: Response<MatchResponse>) {
                if (response.isSuccessful) {
                    // Obtém e organiza as partidas: finalizadas primeiro, depois as futuras
                    val partidas = response.body()?.matches ?: emptyList()
                    val partidasOrdenadas = partidas
                        .filter { it.score.fullTime.home != null && it.score.fullTime.away != null }
                        .sortedByDescending { it.matchday ?: 0 } +
                            partidas.filter { it.score.fullTime.home == null || it.score.fullTime.away == null }
                                .sortedBy { it.matchday ?: 0 }

                    // Define o adapter do RecyclerView e ação de clique para abrir detalhes
                    recycler.adapter = PartidasAdapter(partidasOrdenadas) { partida ->
                        requireActivity().findViewById<View>(R.id.viewPager).visibility = View.GONE
                        requireActivity().findViewById<View>(R.id.tabLayout).visibility = View.GONE
                        requireActivity().findViewById<View>(R.id.fragmentContainer).visibility = View.VISIBLE

                        val fragment = PartidaDetalheFragment.novaInstancia(partida)
                        requireActivity().supportFragmentManager.beginTransaction()
                            .replace(R.id.fragmentContainer, fragment)
                            .addToBackStack(null)
                            .commit()
                    }
                } else {
                    // Exibe mensagem de erro se a resposta não for bem-sucedida
                    Toast.makeText(context, "Erro ao carregar resultados", Toast.LENGTH_SHORT).show()
                }
            }
            override fun onFailure(call: Call<MatchResponse>, t: Throwable) {
                // Exibe mensagem de erro em caso de falha na conexão
                Toast.makeText(context, "Falha na conexão", Toast.LENGTH_SHORT).show()
            }
        })
        return view
    }
}