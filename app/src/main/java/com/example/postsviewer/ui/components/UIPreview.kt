package com.example.postsviewer.ui.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.postsviewer.data.Post
import com.example.postsviewer.presentation.DetailsUiState
import com.example.postsviewer.presentation.PostUiState
import com.example.postsviewer.ui.screens.PostDetailsContent
import com.example.postsviewer.ui.screens.PostListContent

@Preview(showBackground = true)
@Composable
fun PreviewPostListContent() {
    val fakePosts = listOf(Post(1, "Hello World", "This is a preview", 1))
    PostListContent(
        uiState = PostUiState.Success(fakePosts),
        onPostClick = {},
        onRetry = {}
    )
}

@Preview(showBackground = true)
@Composable
fun PreviewPostDetailsContent() {
    val samplePost = Post(id = 1, title = "Compose screen", body = "Some content about compose and its features!", userId = 1)
    PostDetailsContent(
        uiState = DetailsUiState.Success(samplePost),
        onRetry = {},
        onBackClick = {}
    )
}