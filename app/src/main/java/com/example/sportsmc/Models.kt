package com.example.sportsmc

// Para resultados de partidas
data class MatchResponse(val matches: List<Match>)
data class Match(
    val homeTeam: Team,
    val awayTeam: Team,
    val score: Score,
    val utcDate: String,
    val matchday: Int?,
    val venue: String?, // Estádio
    val lineups: List<Lineup>?, // Escalações
    val goals: List<Goal>?, // Gols
    val bookings: List<Card>? // Cartões
)
data class Team(val name: String)
data class Score(val fullTime: FullTime)
data class FullTime(val home: Int?, val away: Int?)

// Escalações
data class Lineup(val team: Team, val formation: String?, val players: List<Player>)
// Gols
data class Goal(val minute: Int, val scorer: Player, val team: Team)
// Cartões
data class Card(val minute: Int, val player: Player, val team: Team, val card: String) // "Yellow" ou "Red"

// Para artilheiros
data class ScorersResponse(val scorers: List<Scorer>)
data class Scorer(val player: Player, val team: Team, val goals: Int)
data class Player(val name: String)

// Para tabela
data class StandingsResponse(val standings: List<Standing>)
data class Standing(val table: List<TableItem>)
data class TableItem(
    val position: Int,
    val team: Team,
    val points: Int,
    val playedGames: Int,
    val won: Int,
    val draw: Int,
    val lost: Int,
    val goalsFor: Int,
    val goalsAgainst: Int,
    val goalDifference: Int
)