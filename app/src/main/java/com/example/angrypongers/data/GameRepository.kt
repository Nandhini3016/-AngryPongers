package com.example.angrypongers.data

import com.example.angrypongers.domain.model.GameResult
import com.example.angrypongers.domain.model.GameState


interface GameRepository {

    suspend fun saveGameState(gameState: GameState)
    suspend fun loadGameState(): GameState?
    suspend fun saveGameResult(result: GameResult)
    suspend fun getHighScores(): List<GameResult>
}