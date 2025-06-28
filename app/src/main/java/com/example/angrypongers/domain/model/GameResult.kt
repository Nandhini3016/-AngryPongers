package com.example.angrypongers.domain.model

sealed class GameResult {
    object DayVictory : GameResult()
    object NightVictory : GameResult()
    object Draw : GameResult()
    data class TimeExpired(val winner: Team) : GameResult()
}

enum class Team { Day, Night }
