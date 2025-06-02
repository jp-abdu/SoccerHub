package com.example.sportsmc

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

// Adapter para exibir a lista de artilheiros em um RecyclerView
class ArtilheirosAdapter(private val artilheiros: List<Scorer>) : RecyclerView.Adapter<ArtilheirosAdapter.ViewHolder>() {

    // ViewHolder representa cada item da lista
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val imgEscudoTime: ImageView = view.findViewById(R.id.imgEscudoTime) // Imagem do escudo do time
        val txtNomeJogador: TextView = view.findViewById(R.id.txtNomeJogador) // Nome do jogador
        val txtGols: TextView = view.findViewById(R.id.txtGols) // Quantidade de gols
    }

    // Cria uma nova ViewHolder quando necessário
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // Infla o layout do item da lista
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_artilheiro, parent, false)
        return ViewHolder(v)
    }

    // Associa os dados do artilheiro à ViewHolder
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val artilheiro = artilheiros[position]
        holder.txtNomeJogador.text = artilheiro.player.name // Define o nome do jogador
        holder.txtGols.text = artilheiro.goals.toString() // Define a quantidade de gols

        // Carrega a imagem do escudo do time usando Glide
        Glide.with(holder.itemView)
            .load(artilheiro.team.crest)
            .placeholder(R.drawable.ic_launcher_background) // Imagem de placeholder
            .into(holder.imgEscudoTime)
    }

    // Retorna a quantidade de itens na lista
    override fun getItemCount() = artilheiros.size
}