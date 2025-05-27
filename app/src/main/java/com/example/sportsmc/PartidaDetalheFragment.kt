package com.example.sportsmc

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide

class PartidaDetalheFragment : Fragment() {
    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_partida_detalhe, container, false)
        val data = arguments?.getString("data")
        val placar = arguments?.getString("placar")
        val rodada = arguments?.getString("rodada")
        val escudoCasa = arguments?.getString("escudoCasa")
        val escudoFora = arguments?.getString("escudoFora")
        val nomeCasa = arguments?.getString("home")
        val nomeFora = arguments?.getString("away")

        view.findViewById<TextView>(R.id.txtDetalheData).text = data
        view.findViewById<TextView>(R.id.txtDetalhePlacar).text = placar
        view.findViewById<TextView>(R.id.txtDetalheRodada).text = "Rodada: $rodada"

        // Atualiza os nomes dos times
        view.findViewById<TextView>(R.id.txtTimeCasa).text = nomeCasa
        view.findViewById<TextView>(R.id.txtTimeFora).text = nomeFora

        val btnFechar = view.findViewById<Button>(R.id.btnFechar)
        btnFechar.setOnClickListener {
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }
        val imgEscudoCasa = view.findViewById<ImageView>(R.id.imgEscudoCasa)
        val imgEscudoFora = view.findViewById<ImageView>(R.id.imgEscudoFora)

        Glide.with(this)
            .load(escudoCasa)
            .placeholder(R.drawable.ic_launcher_background)
            .into(imgEscudoCasa)

        Glide.with(this)
            .load(escudoFora)
            .placeholder(R.drawable.ic_launcher_background)
            .into(imgEscudoFora)

        view.findViewById<Button>(R.id.btnVoltar)?.setOnClickListener {
            requireActivity().findViewById<View>(R.id.fragmentContainer).visibility = View.GONE
            requireActivity().findViewById<View>(R.id.viewPager).visibility = View.VISIBLE
            parentFragmentManager.popBackStack()
        }

        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        requireActivity().findViewById<View>(R.id.viewPager).visibility = View.VISIBLE
        requireActivity().findViewById<View>(R.id.tabLayout).visibility = View.VISIBLE
        requireActivity().findViewById<View>(R.id.fragmentContainer).visibility = View.GONE
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
            args.putString("escudoCasa", match.homeTeam.crest)
            args.putString("escudoFora", match.awayTeam.crest)
            fragment.arguments = args
            return fragment
        }
    }
}