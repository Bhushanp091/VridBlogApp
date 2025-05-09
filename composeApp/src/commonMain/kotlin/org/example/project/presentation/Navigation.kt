package org.example.project.presentation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

import org.example.project.presentation.screen.BlogListScreen
import org.example.project.presentation.screen.WebViewScreen
import org.example.project.presentation.viewModel.BlogViewModel
import org.koin.compose.koinInject


@Composable
fun Navigation(){

    val navController = rememberNavController()
    val viewModel = koinInject<BlogViewModel>()

    NavHost(navController = navController, startDestination = Screen.BlogList.route){
        composable(route = Screen.BlogList.route){
            BlogListScreen(viewModel){
                navController.navigate(Screen.WebView.route)
            }
        }
        composable(Screen.WebView.route) {
            WebViewScreen(url = viewModel.webUrl.value, onBackClick = { navController.popBackStack() })
        }
    }
}


sealed class Screen(val route: String) {
    object BlogList : Screen("blogList")
    object WebView : Screen("webView/{url}")
}