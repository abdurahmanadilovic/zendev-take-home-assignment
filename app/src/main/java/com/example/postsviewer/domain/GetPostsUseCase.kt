package com.example.postsviewer.domain

import com.example.postsviewer.data.Post
import com.example.postsviewer.data.PostRepository
import kotlinx.coroutines.withContext
import kotlin.coroutines.CoroutineContext

class GetPostsUseCase(private val coroutineContext: CoroutineContext, private val repository: PostRepository) {
    suspend operator fun invoke(): Result<List<Post>> {
        return withContext(coroutineContext) {
            getResult {
                val posts = repository.getPosts()
                posts.filter { it.id < 50 }
            }
        }
    }
}