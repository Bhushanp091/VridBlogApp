package org.example.project.expect

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
expect fun WebView(url: String, modifier: Modifier = Modifier,onShowLoader:(Boolean)->Unit)