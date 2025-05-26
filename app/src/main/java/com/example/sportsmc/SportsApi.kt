package com.example.sportsmc

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface SportsApi {
    @GET("eventspastleague.php")
    fun listarEventos(@Query("id") leagueId: String): Call<EventoResponse>
}