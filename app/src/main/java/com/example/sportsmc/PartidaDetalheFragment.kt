package com.example.sportsmc

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment

class PartidaDetalheFragment : Fragment() {
    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_partida_detalhe, container, false)
        val home = arguments?.getString("home")
        val away = arguments?.getString("away")
        val data = arguments?.getString("data")
        val placar = arguments?.getString("placar")
        val rodada = arguments?.getString("rodada")

        view.findViewById<TextView>(R.id.txtDetalheTimes).text = "$home x $away"
        view.findViewById<TextView>(R.id.txtDetalheData).text = data
        view.findViewById<TextView>(R.id.txtDetalhePlacar).text = placar
        view.findViewById<TextView>(R.id.txtDetalheRodada).text = "Rodada: $rodada"

        return view
    }

    companion object {
        fun novaInstancia(match: Match): PartidaDetalheFragment {
            val fragment = PartidaDetalheFragment()
            val args = Bundle()
            args.putString("home", match.homeTeam.name)
            args.putString("away", match.awayTeam.name)
            args.putString("data", match.utcDate.substring(0, 10))
            val placar = match.score.fullTime
            args.putString(
                "placar",
                if (placar.home != null && placar.away != null)
                    "${placar.home} - ${placar.away}"
                else
                    "A definir"
            )
            args.putString("rodada", match.matchday?.toString() ?: "N/A")
            fragment.arguments = args
            return fragment
        }
    }
}