package com.example.sportsmc

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path

interface FootballDataApi {
    @GET("v4/competitions/{competition}/matches")
    fun listarPartidas(
        @Path("competition") competition: String,
        @Header("X-Auth-Token") apiKey: String
    ): Call<MatchResponse>

    @GET("v4/competitions/{competition}/scorers")
    fun listarArtilheiros(
        @Path("competition") competition: String,
        @Header("X-Auth-Token") apiKey: String
    ): Call<ScorersResponse>

    @GET("v4/competitions/{competition}/standings")
    fun listarTabela(
        @Path("competition") competition: String,
        @Header("X-Auth-Token") apiKey: String
    ): Call<StandingsResponse>
    @GET("v4/teams/{id}")
    fun getTeam(
        @Path("id") teamId: Int,
        @Header("X-Auth-Token") apiKey: String
    ): Call<TeamDetailResponse>
}