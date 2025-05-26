package com.example.sportsmc

// Para resultados de partidas
data class MatchResponse(val matches: List<Match>)
data class Match(
    val homeTeam: Team,
    val awayTeam: Team,
    val score: Score,
    val utcDate: String,
    val matchday: Int? // <-- Novo campo para a rodada
)
data class Team(val name: String)
data class Score(val fullTime: FullTime)
data class FullTime(val home: Int?, val away: Int?)

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