package com.example.sportsmc

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path

// Interface que define os endpoints da API de dados de futebol para uso com Retrofit
interface FootballDataApi {
    // Endpoint para listar partidas de uma competição específica
    @GET("v4/competitions/{competition}/matches")
    fun listarPartidas(
        @Path("competition") competition: String, // Código da competição (ex: "BSA")
        @Header("X-Auth-Token") apiKey: String   // Chave de autenticação da API
    ): Call<MatchResponse>

    // Endpoint para listar artilheiros de uma competição
    @GET("v4/competitions/{competition}/scorers")
    fun listarArtilheiros(
        @Path("competition") competition: String,
        @Header("X-Auth-Token") apiKey: String
    ): Call<ScorersResponse>

    // Endpoint para listar a tabela de classificação de uma competição
    @GET("v4/competitions/{competition}/standings")
    fun listarTabela(
        @Path("competition") competition: String,
        @Header("X-Auth-Token") apiKey: String
    ): Call<StandingsResponse>

    // Endpoint para obter detalhes de um time pelo ID
    @GET("v4/teams/{id}")
    fun getTeam(
        @Path("id") teamId: Int,                 // ID do time
        @Header("X-Auth-Token") apiKey: String
    ): Call<TeamDetailResponse>
}