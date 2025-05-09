package org.example.project.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.TextUnit
import network.chaintech.sdpcomposemultiplatform.ssp

@Composable
fun GeneralTextView(
    modifier: Modifier = Modifier,
    title: String,
    fontSize: TextUnit = 10.ssp,
    textAlign: TextAlign = TextAlign.Start,
    textColor: Color = Color.Black,
    maxLines: Int = Int.MAX_VALUE,
    fontWeight: FontWeight = FontWeight.W400,
    textDecoration: TextDecoration? = null,
    overflow: TextOverflow = TextOverflow.Ellipsis,
    brush: Brush? = null,
    letterSpacing: TextUnit = TextUnit.Unspecified,
    lineHeight: TextUnit = TextUnit.Unspecified,
    onTextLayout: (TextLayoutResult) -> Unit = {},
    onClick: (() -> Unit?)? = null
) {
    Text(
        text = title,
        fontSize = fontSize,
        color = textColor,
        textAlign = textAlign,
        fontWeight = fontWeight,
        modifier = if (onClick != null) modifier.clickable { onClick.invoke() } else modifier,
        maxLines = maxLines,
        textDecoration = textDecoration,
        style = TextStyle(
            brush = brush,
        ),
        lineHeight = lineHeight,
        letterSpacing = letterSpacing,
        onTextLayout = onTextLayout,
        overflow = overflow
    )
}