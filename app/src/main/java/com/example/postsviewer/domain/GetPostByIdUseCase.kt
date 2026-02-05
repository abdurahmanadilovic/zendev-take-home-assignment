package com.example.postsviewer.domain

import com.example.postsviewer.data.Post
import com.example.postsviewer.data.PostRepository

class GetPostByIdUseCase(private val repository: PostRepository) {
    suspend operator fun invoke(id: Int): Result<Post> = try {
        Result.success(repository.getPostById(id))
    } catch (e: Exception) {
        Result.failure(e)
    }
}