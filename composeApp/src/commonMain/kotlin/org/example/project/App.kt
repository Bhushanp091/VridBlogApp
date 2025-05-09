package org.example.project

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import org.example.project.di.appModule
import org.example.project.presentation.Navigation
import org.example.project.presentation.screen.BlogListScreen
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.KoinApplication

@Composable
@Preview
fun App() {
    KoinApplication(application = { modules(appModule) }) {
        MaterialTheme {
            Navigation()
        }
    }
}