package com.example.angrypongers

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.ViewModelProvider
import com.example.angrypongers.data.InMemoryGameRepository
import com.example.angrypongers.presentation.GameScreen
import com.example.angrypongers.presentation.GameViewModel

class MainActivity : ComponentActivity() {

    private val repository = InMemoryGameRepository()

    private val viewModel by lazy {
        ViewModelProvider(
            this,
            object : ViewModelProvider.Factory {
                @Suppress("UNCHECKED_CAST")
                override fun <T : androidx.lifecycle.ViewModel> create(modelClass: Class<T>): T {
                    return GameViewModel(repository) as T
                }
            }
        ).get(GameViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            GameScreen(viewModel = viewModel)
        }
    }
}