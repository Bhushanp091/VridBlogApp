package org.example.project.data.repo

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.example.project.domain.BlogApi
import org.example.project.domain.model.BlogData


class BlogRepository(private val api: BlogApi) {
    fun getBlogs(page: Int = 1, perPage: Int = 10): Flow<Result<List<BlogData>>> = flow {
        try {
            val blogs = api.getBlogs(page, perPage)
            emit(Result.success(blogs))
        } catch (e: Exception) {
            emit(Result.failure(e))
        }
    }
}
