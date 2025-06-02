package com.example.sportsmc

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

// Adapter responsável por fornecer os fragments para cada aba do ViewPager2 na MainActivity
class MainPagerAdapter(activity: FragmentActivity) : FragmentStateAdapter(activity) {
    // Define o número de abas/fragments
    override fun getItemCount() = 3

    // Retorna o fragment correspondente à posição da aba
    override fun createFragment(position: Int): Fragment = when (position) {
        0 -> ResultadosFragment()      // Aba 0: Resultados das partidas
        1 -> ArtilheirosFragment()     // Aba 1: Lista de artilheiros
        2 -> TabelaFragment()          // Aba 2: Tabela de classificação
        else -> throw IllegalArgumentException() // Caso inválido
    }
}