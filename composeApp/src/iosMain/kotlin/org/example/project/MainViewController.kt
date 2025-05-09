package org.example.project

import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.window.ComposeUIViewController
import platform.UIKit.UIApplication
import platform.UIKit.setStatusBarHidden

fun MainViewController() = ComposeUIViewController {
    App()
}