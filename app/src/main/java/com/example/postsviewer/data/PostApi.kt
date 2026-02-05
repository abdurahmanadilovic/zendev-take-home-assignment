package com.example.postsviewer.data

import retrofit2.http.GET
import retrofit2.http.Path

interface PostApi {
    @GET("posts")
    suspend fun getPosts(): List<Post>

    @GET("posts/{id}")
    suspend fun getPostById(@Path("id") id: Int): Post

    companion object {
        const val BACKEND_URL = "https://jsonplaceholder.typicode.com/"
    }
}
