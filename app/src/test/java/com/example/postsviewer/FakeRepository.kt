package com.example.postsviewer

import com.example.postsviewer.data.Post
import com.example.postsviewer.data.PostRepository

class FakePostRepository : PostRepository {
    var postsToReturn: List<Post> = emptyList()
    var postToReturn: Post? = null
    var exceptionToThrow: Exception? = null

    override suspend fun getPosts(): List<Post> {
        exceptionToThrow?.let { throw it }
        return postsToReturn
    }

    override suspend fun getPostById(id: Int): Post {
        exceptionToThrow?.let { throw it }
        return postToReturn ?: throw NoSuchElementException("Post not found")
    }
}
