package com.example.sportsmc

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment

class NoticiaDetalheFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_noticia_detalhe, container, false)
        val conteudo = arguments?.getString("conteudo")
        view.findViewById<TextView>(R.id.txtDetalheNoticia).text = conteudo
        return view
    }

    companion object {
        fun novaInstancia(evento: Evento): NoticiaDetalheFragment {
            val fragment = NoticiaDetalheFragment()
            val args = Bundle()
            args.putString("conteudo", evento.strDescriptionEN ?: "Sem descrição")
            fragment.arguments = args
            return fragment
        }
    }
}