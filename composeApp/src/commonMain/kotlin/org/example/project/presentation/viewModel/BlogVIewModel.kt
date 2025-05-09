package org.example.project.presentation.viewModel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.example.project.data.repo.BlogRepository
import org.example.project.domain.model.BlogData
import org.example.project.domain.model.BlogList
import org.example.project.domain.model.BlogUiState
import org.example.project.utils.Resource

class BlogViewModel(private val repository: BlogRepository) {
    private val viewModelScope = CoroutineScope(SupervisorJob() + Dispatchers.Main)

    private val _uiState = MutableStateFlow<BlogUiState>(BlogUiState.Loading)
    val uiState: StateFlow<BlogUiState> = _uiState.asStateFlow()

    val webUrl = mutableStateOf("")

    private var currentPage = 1
    private var blogs = mutableListOf<BlogData>()
    private var isLastPage = false

    init {
        loadBlogs(refresh = true)
    }

    fun loadBlogs(refresh: Boolean = false, perPage: Int = 10) {
        if (refresh) {
            currentPage = 1
            blogs.clear()
            _uiState.value = BlogUiState.Loading
        } else {
            // Only set to loading more if we're not already in a loading state
            if (_uiState.value is BlogUiState.Success) {
                _uiState.value = BlogUiState.Success(
                    blogs = blogs,
                    currentPage = currentPage,
                    isLastPage = isLastPage,
                    isLoadingMore = true
                )
            }
        }

        viewModelScope.launch {
            repository.getBlogs(currentPage, perPage).collectLatest { result ->
                result.fold(
                    onSuccess = { newBlogs ->
                        if (newBlogs.isEmpty()) {
                            isLastPage = true
                        } else {
                            blogs.addAll(newBlogs)
                            if (newBlogs.size < perPage) {
                                isLastPage = true
                            }
                        }
                        _uiState.value = BlogUiState.Success(
                            blogs = blogs.toList(),
                            currentPage = currentPage,
                            isLastPage = isLastPage,
                            isLoadingMore = false
                        )
                    },
                    onFailure = { exception ->
                        _uiState.value = BlogUiState.Error(
                            message = exception.message ?: "Unknown error occurred"
                        )
                    }
                )
            }
        }
    }

    fun loadNextPage() {
        if ((_uiState.value as? BlogUiState.Success)?.isLoadingMore == true ||
            (_uiState.value as? BlogUiState.Success)?.isLastPage == true) {
            return // Prevent multiple simultaneous pagination requests or loading past the end
        }
        currentPage++
        loadBlogs(refresh = false)
    }

    fun refreshBlogs() {
        loadBlogs(refresh = true)
    }
}