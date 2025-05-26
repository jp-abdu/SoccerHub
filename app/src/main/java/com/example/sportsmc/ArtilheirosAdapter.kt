package com.example.sportsmc

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ArtilheirosAdapter(private val artilheiros: List<Scorer>) : RecyclerView.Adapter<ArtilheirosAdapter.ViewHolder>() {
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val txtArtilheiro: TextView = view.findViewById(android.R.id.text1)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(android.R.layout.simple_list_item_1, parent, false)
        return ViewHolder(v)
    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val artilheiro = artilheiros[position]
        holder.txtArtilheiro.text = "${artilheiro.player.name} (${artilheiro.team.name}) - ${artilheiro.goals} gols"
    }
    override fun getItemCount() = artilheiros.size
}