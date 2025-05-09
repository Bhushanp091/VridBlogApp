package org.example.project.expect

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.interop.UIKitView
import androidx.compose.ui.viewinterop.UIKitInteropProperties
import androidx.compose.ui.viewinterop.UIKitView
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.readValue
import platform.Foundation.NSURL
import platform.WebKit.WKWebView
import platform.WebKit.WKWebViewConfiguration

@OptIn(ExperimentalForeignApi::class)
@Composable
actual fun WebView(url: String, modifier: Modifier,onShowLoader:(Boolean)->Unit) {
    UIKitView<WKWebView>(
        factory = {
            val webView = WKWebView(
                frame = platform.CoreGraphics.CGRectZero.readValue(),
                configuration = WKWebViewConfiguration()
            )
            val nsUrl = NSURL(string = url)
            val request = platform.Foundation.NSURLRequest(uRL = nsUrl)
            webView.loadRequest(request)
            webView
        },
        modifier = modifier,
        update = { webView ->
            val nsUrl = NSURL(string = url)
            val request = platform.Foundation.NSURLRequest(uRL = nsUrl)
            webView.loadRequest(request)
        },
        properties = UIKitInteropProperties(
            isInteractive = true,
            isNativeAccessibilityEnabled = true
        )
    )
}