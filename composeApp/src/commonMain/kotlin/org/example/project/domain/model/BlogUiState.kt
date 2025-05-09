package org.example.project.domain.model

sealed class BlogUiState {
    object Loading : BlogUiState()
    data class Success(
        val blogs: List<BlogData>,
        val currentPage: Int,
        val isLastPage: Boolean,
        val isLoadingMore: Boolean
    ) : BlogUiState()
    data class Error(val message: String) : BlogUiState()
}