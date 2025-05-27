package com.example.sportsmc

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class PartidasAdapter(
    private val partidas: List<Match>,
    private val onClick: (Match) -> Unit
) : RecyclerView.Adapter<PartidasAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val imgEscudoCasa: ImageView = view.findViewById(R.id.imgEscudoCasa)
        val imgEscudoFora: ImageView = view.findViewById(R.id.imgEscudoFora)
        val txtTimeCasa: TextView = view.findViewById(R.id.txtTimeCasa)
        val txtTimeFora: TextView = view.findViewById(R.id.txtTimeFora)
        val txtGolsCasa: TextView = view.findViewById(R.id.txtGolsCasa)
        val txtGolsFora: TextView = view.findViewById(R.id.txtGolsFora)
        val txtData: TextView = view.findViewById(R.id.txtData)
        val txtRodada: TextView = view.findViewById(R.id.txtRodada) // NOVO
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_partida, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val partida = partidas[position]
        holder.txtTimeCasa.text = partida.homeTeam.name
        holder.txtTimeFora.text = partida.awayTeam.name
        holder.txtData.text = partida.utcDate.substring(0, 10)
        holder.txtRodada.text = "Rodada: ${partida.matchday ?: "-"}" // NOVO

        val golsCasa = partida.score.fullTime.home?.toString() ?: "-"
        val golsFora = partida.score.fullTime.away?.toString() ?: "-"
        holder.txtGolsCasa.text = golsCasa
        holder.txtGolsFora.text = golsFora

        Glide.with(holder.itemView)
            .load(partida.homeTeam.crest)
            .placeholder(R.drawable.ic_launcher_background)
            .into(holder.imgEscudoCasa)

        Glide.with(holder.itemView)
            .load(partida.awayTeam.crest)
            .placeholder(R.drawable.ic_launcher_background)
            .into(holder.imgEscudoFora)

        holder.itemView.setOnClickListener { onClick(partida) }
    }

    override fun getItemCount() = partidas.size
}