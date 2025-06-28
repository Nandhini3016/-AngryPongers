package com.example.angrypongers.data

import com.example.angrypongers.domain.model.GameResult
import com.example.angrypongers.domain.model.GameState


class InMemoryGameRepository : GameRepository {

    private var savedState: GameState? = null
    private val highScores = mutableListOf<GameResult>()

    override suspend fun saveGameState(gameState: GameState) {
        savedState = gameState
    }

    override suspend fun loadGameState(): GameState? {
        return savedState
    }

    override suspend fun saveGameResult(result: GameResult) {
        highScores.add(result)
    }

    override suspend fun getHighScores(): List<GameResult> {
        return highScores.toList()
    }
}
