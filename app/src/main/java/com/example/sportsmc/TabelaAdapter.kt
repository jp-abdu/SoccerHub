package com.example.sportsmc

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

// Adapter do RecyclerView para exibir a tabela de classificação dos times
class TabelaAdapter(private val tabela: List<TableItem>) : RecyclerView.Adapter<TabelaAdapter.ViewHolder>() {

    // ViewHolder que armazena as views de cada item da tabela
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val txtPosicao: TextView = view.findViewById(R.id.txtPosicao)      // Posição do time
        val imgEscudo: ImageView = view.findViewById(R.id.imgEscudo)       // Escudo do time
        val txtTime: TextView = view.findViewById(R.id.txtTime)            // Nome do time
        val txtPontos: TextView = view.findViewById(R.id.txtPontos)        // Pontos
        val txtVitorias: TextView = view.findViewById(R.id.txtVitorias)    // Vitórias
        val txtEmpates: TextView = view.findViewById(R.id.txtEmpates)      // Empates
        val txtDerrotas: TextView = view.findViewById(R.id.txtDerrotas)    // Derrotas
    }

    // Cria o ViewHolder inflando o layout do item da tabela
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_tabela, parent, false)
        return ViewHolder(v)
    }

    // Associa os dados do time às views do ViewHolder
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = tabela[position]
        holder.txtPosicao.text = item.position.toString()
        holder.txtTime.text = item.team.name
        holder.txtPontos.text = item.points.toString()
        holder.txtVitorias.text = item.won.toString()
        holder.txtEmpates.text = item.draw.toString()
        holder.txtDerrotas.text = item.lost.toString()

        // Carrega o escudo do time usando Glide
        Glide.with(holder.itemView)
            .load(item.team.crest)
            .placeholder(R.drawable.ic_launcher_background)
            .into(holder.imgEscudo)

        // Define a cor de fundo da posição conforme a classificação
        val context = holder.itemView.context
        val total = tabela.size
        val bgColor = when (item.position) {
            in 1..4 -> context.getColor(R.color.tabela_verde_escuro)      // G4 (Libertadores)
            5, 6 -> context.getColor(R.color.tabela_verde_claro)          // Pré-Libertadores
            in 7..12 -> context.getColor(R.color.tabela_azul)             // Sul-Americana
            in (total - 3)..total -> context.getColor(R.color.tabela_vermelho) // Z4 (Rebaixamento)
            else -> context.getColor(R.color.tabela_transparente)          // Demais posições
        }
        holder.txtPosicao.setBackgroundColor(bgColor)
    }

    // Retorna o número de itens na tabela
    override fun getItemCount() = tabela.size
}