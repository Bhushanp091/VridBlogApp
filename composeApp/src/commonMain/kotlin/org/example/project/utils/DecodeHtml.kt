package org.example.project.utils


fun decodeHtml(input: String): String {
    return input
        .replace("&#8211;", "–")
        .replace("&#8217;", "’")
        .replace("&amp;", "&")
        .replace("&lt;", "<")
        .replace("&gt;", ">")
        .replace("&quot;", "\"")
        .replace("&apos;", "'")
        .replace("&nbsp;", " ")
        .replace("<br>", "\n")
        .replace(Regex("<.*?>"), "") // Remove any remaining HTML tags
        .trim()
}
