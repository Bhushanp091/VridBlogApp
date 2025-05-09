package org.example.project.domain

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.example.project.domain.model.BlogData

class BlogApi(private val httpClient: HttpClient) {

    suspend fun getBlogs(page: Int = 1, perPage: Int = 10): List<BlogData> {
        return httpClient.get("https://blog.vrid.in/wp-json/wp/v2/posts") {
            parameter("per_page", perPage)
            parameter("page", page)
        }.body()
    }

    companion object {
        fun create(): BlogApi {
            val client = HttpClient {
                install(ContentNegotiation) {
                    json(Json {
                        ignoreUnknownKeys = true
                        prettyPrint = true
                        isLenient = true
                    })
                }
            }
            return BlogApi(client)
        }
    }
}