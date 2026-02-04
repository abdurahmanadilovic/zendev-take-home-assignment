package com.example.postsviewer.data

interface PostRepository {
    suspend fun getPosts(): List<Post>
}

class PostRepositoryImpl(private val api: PostApi) : PostRepository {
    override suspend fun getPosts(): List<Post> = api.getPosts()
}
