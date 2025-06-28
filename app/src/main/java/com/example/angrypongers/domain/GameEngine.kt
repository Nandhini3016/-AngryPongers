package com.example.angrypongers.domain

import androidx.compose.ui.graphics.Color
import com.example.angrypongers.domain.model.Ball
import com.example.angrypongers.domain.model.GameState
import kotlin.math.abs
import kotlin.math.cos
import kotlin.math.floor
import kotlin.math.sign
import kotlin.math.sin
import kotlin.random.Random

object GameEngine {

    private val angles = listOf(
        0.0,
        Math.PI / 4,
        Math.PI / 2,
        3 * Math.PI / 4,
        Math.PI,
        5 * Math.PI / 4,
        3 * Math.PI / 2,
        7 * Math.PI / 4
    )

    fun updateGameState(current: GameState, deltaTime: Long): GameState {
        val updatedBalls = current.balls.map { updateBallPosition(it, deltaTime) }
        val updatedGrid = current.grid.copyOf()

        updatedBalls.forEach { ball ->
            // Multi-point collision check
            for (angle in angles) {
                val checkX = ball.x + cos(angle) * ball.radius
                val checkY = ball.y + sin(angle) * ball.radius
                val gridX = floor(checkX / GameConstants.SQUARE_SIZE).toInt()
                val gridY = floor(checkY / GameConstants.SQUARE_SIZE).toInt()

                if (gridX in 0 until GameConstants.GRID_WIDTH && gridY in 0 until GameConstants.GRID_HEIGHT) {
                    updatedGrid[gridX][gridY] = ball.teamColor
                }
            }
        }

        val (dayScore, nightScore) = calculateScore(updatedGrid)
        return current.copy(
            grid = updatedGrid,
            balls = updatedBalls,
            dayScore = dayScore,
            nightScore = nightScore,
            frameCount = current.frameCount + 1,
            gameTime = current.gameTime + deltaTime
        )
    }

    fun updateBallPosition(ball: Ball, deltaTime: Long): Ball {
        var vX = ball.velocityX
        var vY = ball.velocityY

        val randomnessX = Random.nextDouble(
            (-GameConstants.RANDOMNESS_FACTOR).toDouble(),
            GameConstants.RANDOMNESS_FACTOR.toDouble()
        ).toFloat()
        val randomnessY = Random.nextDouble(
            (-GameConstants.RANDOMNESS_FACTOR).toDouble(),
            GameConstants.RANDOMNESS_FACTOR.toDouble()
        ).toFloat()

        vX += randomnessX
        vY += randomnessY

        vX = vX.coerceIn(-GameConstants.MAX_SPEED, GameConstants.MAX_SPEED)
        vY = vY.coerceIn(-GameConstants.MAX_SPEED, GameConstants.MAX_SPEED)

        if (abs(vX) < GameConstants.MIN_SPEED) vX = sign(vX) * GameConstants.MIN_SPEED
        if (abs(vY) < GameConstants.MIN_SPEED) vY = sign(vY) * GameConstants.MIN_SPEED

        var newX = ball.x + vX
        var newY = ball.y + vY

        if (newX <= ball.radius || newX >= GameConstants.CANVAS_WIDTH - ball.radius) vX = -vX
        if (newY <= ball.radius || newY >= GameConstants.CANVAS_HEIGHT - ball.radius) vY = -vY

        return ball.copy(x = newX, y = newY, velocityX = vX, velocityY = vY)
    }

    fun calculateScore(grid: Array<Array<Color>>): Pair<Int, Int> {
        val day = grid.sumOf { row -> row.count { it == GameConstants.DAY_COLOR } }
        val night = grid.sumOf { row -> row.count { it == GameConstants.NIGHT_COLOR } }
        return Pair(day, night)
    }
}
