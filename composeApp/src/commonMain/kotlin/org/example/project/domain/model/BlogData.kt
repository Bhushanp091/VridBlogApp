package org.example.project.domain.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class BlogData(
    val id: Int,
    val title: RenderedText,
    val slug: String,
    val link: String,
    val date: String,
    val author: Int,
    val content: RenderedText,
    val excerpt: RenderedText,
    @SerialName("featured_media") val featuredMediaId: Int,
    @SerialName("jetpack_featured_media_url") val featuredMediaUrl: String,
    val categories: List<Int>,
    val tags: List<Int>
)

@Serializable
data class RenderedText(
    @SerialName("rendered") val text: String
)

