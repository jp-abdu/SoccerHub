package com.example.sportsmc

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class ArtilheirosAdapter(private val artilheiros: List<Scorer>) : RecyclerView.Adapter<ArtilheirosAdapter.ViewHolder>() {
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val imgEscudoTime: ImageView = view.findViewById(R.id.imgEscudoTime)
        val txtNomeJogador: TextView = view.findViewById(R.id.txtNomeJogador)
        val txtTimeJogador: TextView = view.findViewById(R.id.txtTime)
        val txtGols: TextView = view.findViewById(R.id.txtGols)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_artilheiro, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val artilheiro = artilheiros[position]
        holder.txtNomeJogador.text = artilheiro.player.name
        holder.txtTimeJogador.text = artilheiro.team.name
        holder.txtGols.text = artilheiro.goals.toString()

        Glide.with(holder.itemView)
            .load(artilheiro.team.crest)
            .placeholder(R.drawable.ic_launcher_background)
            .into(holder.imgEscudoTime)
    }

    override fun getItemCount() = artilheiros.size
}