package com.example.angrypongers.presentation

import com.example.angrypongers.domain.GameConstants.CANVAS_HEIGHT
import com.example.angrypongers.domain.GameConstants.CANVAS_WIDTH
import com.example.angrypongers.domain.GameConstants.DAY_COLOR
import com.example.angrypongers.domain.GameConstants.GRID_HEIGHT
import com.example.angrypongers.domain.GameConstants.GRID_WIDTH
import com.example.angrypongers.domain.GameConstants.NIGHT_COLOR
import com.example.angrypongers.domain.GameConstants.SQUARE_SIZE

import androidx.compose.material3.Button
import androidx.compose.material3.Text

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun GameScreen(viewModel: GameViewModel) {
    val state by viewModel.state

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        // 👇 Centered Canvas
        Box(
            modifier = Modifier.align(Alignment.Center)
        ) {
            Canvas(
                modifier = Modifier
                    .size(CANVAS_WIDTH.dp, CANVAS_HEIGHT.dp)
                    .background(Color.Black)
            ) {
                val gridWidthPx = GRID_WIDTH * SQUARE_SIZE.toFloat()
                val gridHeightPx = GRID_HEIGHT * SQUARE_SIZE.toFloat()

                val offsetX = (size.width - gridWidthPx) / 2f
                val offsetY = (size.height - gridHeightPx) / 2f

                // Draw grid
                for (x in 0 until GRID_WIDTH) {
                    for (y in 0 until GRID_HEIGHT) {
                        drawRect(
                            color = state.grid[x][y],
                            topLeft = Offset(
                                offsetX + x * SQUARE_SIZE,
                                offsetY + y * SQUARE_SIZE
                            ),
                            size = Size(SQUARE_SIZE.toFloat(), SQUARE_SIZE.toFloat())
                        )
                    }
                }

                // Draw balls
                state.balls.forEach { ball ->
                    drawCircle(
                        color = ball.ballColor,
                        radius = ball.radius,
                        center = Offset(
                            offsetX + ball.x,
                            offsetY + ball.y
                        )
                    )
                }
            }
        }

        // 👇 Top-aligned UI (score + buttons)
        Column(
            modifier = Modifier.align(Alignment.TopCenter),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("Day: ${state.dayScore}", color = DAY_COLOR, fontSize = 20.sp)
            Text("Night: ${state.nightScore}", color = NIGHT_COLOR, fontSize = 20.sp)
            Row {
                Button(onClick = { viewModel.play() }) { Text("Play") }
                Button(onClick = { viewModel.pause() }) { Text("Pause") }
                Button(onClick = { viewModel.reset() }) { Text("Reset") }
            }
        }
    }
}

