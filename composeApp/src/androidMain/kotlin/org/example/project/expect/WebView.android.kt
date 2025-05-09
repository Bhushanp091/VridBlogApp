package org.example.project.expect

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.ui.viewinterop.AndroidView
import coil3.Bitmap

@Composable
actual fun WebView(url: String, modifier: Modifier,onShowLoader:(Boolean)->Unit) {
    AndroidView(
        factory = { context ->
            android.webkit.WebView(context).apply {
                webViewClient = object : WebViewClient() {
                    override fun onPageStarted(view: android.webkit.WebView, url: String, favicon: Bitmap?) {
                        onShowLoader(true)
                    }

                    override fun onPageFinished(view: android.webkit.WebView, url: String) {
                        onShowLoader(false)
                    }
                }
                settings.javaScriptEnabled = true
                loadUrl(url)
            }
        },
        update = { webView ->
            webView.loadUrl(url)
        },
        modifier = modifier
    )
}
