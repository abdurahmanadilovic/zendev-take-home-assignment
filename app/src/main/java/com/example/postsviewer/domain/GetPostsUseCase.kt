package com.example.postsviewer.domain

import com.example.postsviewer.data.Post
import com.example.postsviewer.data.PostRepository

class GetFilteredPostsUseCase(private val repository: PostRepository) {
    suspend operator fun invoke(): Result<List<Post>> = try {
        val posts = repository.getPosts()
        val filtered = posts.filter { it.id < 50 }
        Result.success(filtered)
    } catch (e: Exception) {
        Result.failure(e)
    }
}