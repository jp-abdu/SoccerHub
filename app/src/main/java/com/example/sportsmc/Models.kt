package com.example.sportsmc

// Modelos para resultados de partidas
data class MatchResponse(val matches: List<Match>) // Resposta da API com lista de partidas

data class Match(
    val homeTeam: Team,         // Time da casa
    val awayTeam: Team,         // Time visitante
    val score: Score,           // Placar da partida
    val utcDate: String,        // Data/hora da partida (UTC)
    val matchday: Int?,         // Rodada (opcional)
)

data class Team(
    val id: Int,                // ID do time
    val name: String,           // Nome do time
    val crest: String?          // URL do escudo do time (opcional)
)

data class Score(val fullTime: FullTime) // Placar final

data class FullTime(val home: Int?, val away: Int?) // Gols de cada time

// Modelos para artilheiros
data class ScorersResponse(val scorers: List<Scorer>) // Resposta da API com lista de artilheiros

data class Scorer(
    val player: Player,         // Jogador artilheiro
    val team: Team,             // Time do jogador
    val goals: Int              // Número de gols
)

data class Player(val name: String) // Nome do jogador

// Modelos para tabela de classificação
data class StandingsResponse(val standings: List<Standing>) // Resposta da API com lista de standings

data class Standing(val table: List<TableItem>) // Lista de times na tabela

data class TableItem(
    val position: Int,          // Posição na tabela
    val team: Team,             // Dados do time
    val points: Int,            // Pontos
    val playedGames: Int,       // Jogos disputados
    val won: Int,               // Vitórias
    val draw: Int,              // Empates
    val lost: Int,              // Derrotas
    val goalsFor: Int,          // Gols a favor
    val goalsAgainst: Int,      // Gols contra
    val goalDifference: Int     // Saldo de gols
)

// Modelo para detalhes de um time
data class TeamDetailResponse(
    val id: Int,                // ID do time
    val name: String,           // Nome do time
    val crest: String?,         // URL do escudo (opcional)
    val founded: Int?,          // Ano de fundação (opcional)
    val venue: String?,         // Estádio (opcional)
    val address: String?,       // Endereço (opcional)
    val website: String?,       // Site oficial (opcional)
    val squad: List<PlayerDetail>? // Lista de jogadores (opcional)
)

data class PlayerDetail(
    val name: String,           // Nome do jogador
    val position: String?       // Posição em campo (opcional)
)