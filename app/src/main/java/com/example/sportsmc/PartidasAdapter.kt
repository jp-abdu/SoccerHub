package com.example.sportsmc

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

// Adapter do RecyclerView para exibir a lista de partidas
class PartidasAdapter(
    private val partidas: List<Match>,                // Lista de partidas a serem exibidas
    private val onClick: (Match) -> Unit              // Callback para clique em uma partida
) : RecyclerView.Adapter<PartidasAdapter.ViewHolder>() {

    // ViewHolder que armazena as views de cada item da lista
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val imgEscudoCasa: ImageView = view.findViewById(R.id.imgEscudoCasa)   // Escudo do time da casa
        val imgEscudoFora: ImageView = view.findViewById(R.id.imgEscudoFora)   // Escudo do time visitante
        val txtTimeCasa: TextView = view.findViewById(R.id.txtTimeCasa)         // Nome do time da casa
        val txtTimeFora: TextView = view.findViewById(R.id.txtTimeFora)         // Nome do time visitante
        val txtGolsCasa: TextView = view.findViewById(R.id.txtGolsCasa)         // Gols do time da casa
        val txtGolsFora: TextView = view.findViewById(R.id.txtGolsFora)         // Gols do time visitante
        val txtData: TextView = view.findViewById(R.id.txtData)                 // Data da partida
        val txtRodada: TextView = view.findViewById(R.id.txtRodada)             // Rodada da partida
    }

    // Cria o ViewHolder inflando o layout do item
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_partida, parent, false)
        return ViewHolder(v)
    }

    // Associa os dados da partida às views do ViewHolder
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val partida = partidas[position]
        holder.txtTimeCasa.text = partida.homeTeam.name
        holder.txtTimeFora.text = partida.awayTeam.name
        holder.txtData.text = partida.utcDate.substring(0, 10)
        holder.txtRodada.text = "Rodada: ${partida.matchday ?: "-"}"

        val golsCasa = partida.score.fullTime.home?.toString() ?: "-"
        val golsFora = partida.score.fullTime.away?.toString() ?: "-"
        holder.txtGolsCasa.text = golsCasa
        holder.txtGolsFora.text = golsFora

        // Carrega os escudos dos times usando Glide
        Glide.with(holder.itemView)
            .load(partida.homeTeam.crest)
            .placeholder(R.drawable.ic_launcher_background)
            .into(holder.imgEscudoCasa)

        Glide.with(holder.itemView)
            .load(partida.awayTeam.crest)
            .placeholder(R.drawable.ic_launcher_background)
            .into(holder.imgEscudoFora)

        // Define o clique no item para acionar o callback
        holder.itemView.setOnClickListener { onClick(partida) }
    }

    // Retorna o número de itens na lista
    override fun getItemCount() = partidas.size
}