package org.example.project.presentation.screen

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import org.example.project.expect.WebView
import org.example.project.presentation.components.CommonToolbar


@Composable
fun WebViewScreen(url: String, onBackClick: () -> Unit) {

//    val state = remember { WebViewState(url = url) }
    var isLoading = remember { mutableStateOf(true) }



    Scaffold(
        topBar = {
            CommonToolbar("Blog") {
                onBackClick()
            }
        }
    ) { paddingValues ->
        WebView(
            url = url,
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            isLoading.value = it
        }

        if (isLoading.value) {
            CircularProgressIndicator()
        }
    }
}


