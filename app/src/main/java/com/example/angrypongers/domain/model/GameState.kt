package com.example.angrypongers.domain.model

import androidx.compose.ui.graphics.Color

data class GameState(
    val grid: Array<Array<Color>>,
    val balls: List<Ball>,
    val dayScore: Int,
    val nightScore: Int,
    val isPlaying: Boolean,
    val frameCount: Long = 0L,
    val gameTime: Long = 0L
)

