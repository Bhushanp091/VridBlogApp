package org.example.project.presentation.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.key.Key.Companion.R
import androidx.compose.ui.text.font.FontWeight
import network.chaintech.sdpcomposemultiplatform.ssp
import org.jetbrains.compose.resources.painterResource
import vridblogapp.composeapp.generated.resources.Res
import vridblogapp.composeapp.generated.resources.icon_arrow_back

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CommonToolbar(
    title: String,
    showBackButton: Boolean = true,
    onBackClick: () -> Unit
) {
    TopAppBar(
        title = {
            GeneralTextView(
                title = title,
                fontSize = 16.ssp,
                fontWeight = FontWeight.W500,
                textColor = Color.White,
            )
        },
        navigationIcon = {
            if (showBackButton) {
                IconButton(onClick = onBackClick) {
                    Icon(
                        painter = painterResource(Res.drawable.icon_arrow_back),
                        contentDescription = "Back"
                    )
                }
            }
        },
        modifier = Modifier.fillMaxWidth(),

        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color(0xFF7C4DFF),  // Violet Color
            titleContentColor = Color.White,
            navigationIconContentColor = Color.White
        ),
        )
}