package com.example.sportsmc

// Para resultados de partidas
data class MatchResponse(val matches: List<Match>)
data class Match(
    val homeTeam: Team,
    val awayTeam: Team,
    val score: Score,
    val utcDate: String,
    val matchday: Int?,
)
data class Team(
    val id: Int,
    val name: String,
    val crest: String? // Adicionado para suportar o escudo do time
)
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

data class TeamDetailResponse(
    val id: Int,
    val name: String,
    val crest: String?,
    val founded: Int?,
    val venue: String?,
    val address: String?,
    val website: String?,
    val squad: List<PlayerDetail>?
)
data class PlayerDetail(
    val name: String,
    val position: String?
)