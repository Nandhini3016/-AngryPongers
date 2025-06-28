package com.example.angrypongers.domain

import androidx.compose.ui.graphics.Color

object GameConstants {

    // 🎮 Game Board Dimensions
    const val CANVAS_WIDTH = 600
    const val CANVAS_HEIGHT = 600

    const val SQUARE_SIZE = 25
    const val GRID_WIDTH = CANVAS_WIDTH / SQUARE_SIZE  // 24 squares wide
    const val GRID_HEIGHT = CANVAS_HEIGHT / SQUARE_SIZE // 24 squares tall

    // ⚡ Ball Physics
    const val BALL_RADIUS = SQUARE_SIZE / 2f  // 12.5 pixels
    const val MIN_SPEED = 5.0f
    const val MAX_SPEED = 10.0f
    const val INITIAL_VELOCITY = 8.0f
    const val RANDOMNESS_FACTOR = 0.01f

    // ⏱️ Timing
    const val TARGET_FPS = 100
    const val FRAME_DURATION_MS = 1000 / TARGET_FPS  // 10ms per frame

    // 🎨 Colors (Material Design inspired)
    val DAY_COLOR = Color(0xFFD9E8E3)        // MysticMint
    val DAY_BALL_COLOR = Color(0xFF114C5A)   // NocturnalExpedition
    val NIGHT_COLOR = Color(0xFF114C5A)      // NocturnalExpedition
    val NIGHT_BALL_COLOR = Color(0xFFD9E8E3) // MysticMint

    // 🕒 Game Rules
    const val MAX_GAME_TIME = 5 * 60 * 1000L // Optional: 5 minutes max, in ms
}
