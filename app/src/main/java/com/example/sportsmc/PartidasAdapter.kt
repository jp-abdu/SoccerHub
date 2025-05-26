package com.example.sportsmc

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class PartidasAdapter(private val partidas: List<Match>) : RecyclerView.Adapter<PartidasAdapter.ViewHolder>() {
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val txtJogo: TextView = view.findViewById(android.R.id.text1)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(android.R.layout.simple_list_item_1, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val partida = partidas[position]
        val placarCasa = partida.score.fullTime.home
        val placarFora = partida.score.fullTime.away
        val rodada = partida.matchday?.toString() ?: "N/A"

        holder.txtJogo.text = if (placarCasa != null && placarFora != null) {
            "Rodada $rodada: ${partida.homeTeam.name} $placarCasa - $placarFora ${partida.awayTeam.name}"
        } else {
            "Rodada $rodada: ${partida.homeTeam.name} x ${partida.awayTeam.name} (A definir)"
        }
    }

    override fun getItemCount() = partidas.size
}