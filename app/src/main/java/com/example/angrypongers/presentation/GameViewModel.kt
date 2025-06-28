package com.example.angrypongers.presentation


import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.angrypongers.data.GameRepository
import com.example.angrypongers.domain.GameConstants.BALL_RADIUS
import com.example.angrypongers.domain.GameConstants.CANVAS_HEIGHT
import com.example.angrypongers.domain.GameConstants.CANVAS_WIDTH
import com.example.angrypongers.domain.GameConstants.DAY_BALL_COLOR
import com.example.angrypongers.domain.GameConstants.DAY_COLOR
import com.example.angrypongers.domain.GameConstants.FRAME_DURATION_MS
import com.example.angrypongers.domain.GameConstants.GRID_HEIGHT
import com.example.angrypongers.domain.GameConstants.GRID_WIDTH
import com.example.angrypongers.domain.GameConstants.INITIAL_VELOCITY
import com.example.angrypongers.domain.GameConstants.NIGHT_BALL_COLOR
import com.example.angrypongers.domain.GameConstants.NIGHT_COLOR
import com.example.angrypongers.domain.GameEngine
import com.example.angrypongers.domain.model.Ball
import com.example.angrypongers.domain.model.GameResult
import com.example.angrypongers.domain.model.GameState
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch


class GameViewModel(
    private val repository: GameRepository
) : ViewModel() {

    private val gameEngine = GameEngine
    private val _state = mutableStateOf(initializeGameState())
    val state: State<GameState> = _state

    private var frameJob: Job? = null

    fun play() {
        if (_state.value.isPlaying) return

        _state.value = _state.value.copy(isPlaying = true)
        frameJob = viewModelScope.launch {
            while (isActive) {
                _state.value = gameEngine.updateGameState(_state.value, FRAME_DURATION_MS.toLong())
                saveGame() // Save each frame (or throttle if needed)

                delay(FRAME_DURATION_MS.toLong())
            }
        }
    }

    fun pause() {
        frameJob?.cancel()
        _state.value = _state.value.copy(isPlaying = false)
    }

    fun reset() {
        pause()
        _state.value = initializeGameState()
    }

    fun loadGame() {
        viewModelScope.launch {
            repository.loadGameState()?.let {
                _state.value = it
            }
        }
    }

    private fun saveGame() {
        viewModelScope.launch {
            repository.saveGameState(_state.value)
        }
    }

    fun saveResult(result: GameResult) {
        viewModelScope.launch {
            repository.saveGameResult(result)
        }
    }

    private fun initializeGameState(): GameState {
        val grid = Array(GRID_WIDTH) { x ->
            Array(GRID_HEIGHT) { _ ->
                if (x < GRID_WIDTH / 2) DAY_COLOR else NIGHT_COLOR
            }
        }

        val dayBall = Ball(
            x = CANVAS_WIDTH / 4f,
            y = CANVAS_HEIGHT / 2f,
            velocityX = INITIAL_VELOCITY,
            velocityY = -INITIAL_VELOCITY,
            teamColor = DAY_COLOR,
            ballColor = DAY_BALL_COLOR,
            radius = BALL_RADIUS
        )

        val nightBall = Ball(
            x = CANVAS_WIDTH * 3 / 4f,
            y = CANVAS_HEIGHT / 2f,
            velocityX = -INITIAL_VELOCITY,
            velocityY = INITIAL_VELOCITY,
            teamColor = NIGHT_COLOR,
            ballColor = NIGHT_BALL_COLOR,
            radius = BALL_RADIUS
        )

        return GameState(
            grid = grid,
            balls = listOf(dayBall, nightBall),
            dayScore = 0,
            nightScore = 0,
            isPlaying = false
        )
    }
}
