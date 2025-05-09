package org.example.project.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class BlogList(
    val blogList: List<BlogData>
)