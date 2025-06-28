package com.example.angrypongers.domain.model


import androidx.compose.ui.graphics.Color

data class Ball(
    val x: Float,
    val y: Float,
    val velocityX: Float,
    val velocityY: Float,
    val teamColor: Color,
    val ballColor: Color,
    val radius: Float
)
