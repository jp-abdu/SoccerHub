package com.example.sportsmc

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class TabelaAdapter(private val tabela: List<TableItem>) : RecyclerView.Adapter<TabelaAdapter.ViewHolder>() {
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val txtPosicao: TextView = view.findViewById(R.id.txtPosicao)
        val imgEscudo: ImageView = view.findViewById(R.id.imgEscudo)
        val txtTime: TextView = view.findViewById(R.id.txtTime)
        val txtPontos: TextView = view.findViewById(R.id.txtPontos)
        val txtVitorias: TextView = view.findViewById(R.id.txtVitorias)
        val txtEmpates: TextView = view.findViewById(R.id.txtEmpates)
        val txtDerrotas: TextView = view.findViewById(R.id.txtDerrotas)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_tabela, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = tabela[position]
        holder.txtPosicao.text = item.position.toString()
        holder.txtTime.text = item.team.name
        holder.txtPontos.text = item.points.toString()
        holder.txtVitorias.text = item.won.toString()
        holder.txtEmpates.text = item.draw.toString()
        holder.txtDerrotas.text = item.lost.toString()

        Glide.with(holder.itemView)
            .load(item.team.crest)
            .placeholder(R.drawable.ic_launcher_background)
            .into(holder.imgEscudo)

        val context = holder.itemView.context
        val total = tabela.size
        val bgColor = when (item.position) {
            in 1..4 -> context.getColor(R.color.tabela_verde_escuro)
            5, 6 -> context.getColor(R.color.tabela_verde_claro)
            in 7..12 -> context.getColor(R.color.tabela_azul)
            in (total - 3)..total -> context.getColor(R.color.tabela_vermelho)
            else -> context.getColor(R.color.tabela_transparente)
        }
        holder.txtPosicao.setBackgroundColor(bgColor)
    }

    override fun getItemCount() = tabela.size
}