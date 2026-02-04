package com.example.postsviewer.data

interface PostRepository {
    suspend fun getPosts(): List<Post>

    suspend fun getPostById(id: Int): Post
}

class PostRepositoryImpl(private val api: PostApi) : PostRepository {
    override suspend fun getPosts(): List<Post> = api.getPosts()
    override suspend fun getPostById(id: Int): Post = api.getPostById(id)
}
