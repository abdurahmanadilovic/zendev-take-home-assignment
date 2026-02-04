package com.example.postsviewer.ui.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.postsviewer.data.Post
import com.example.postsviewer.presentation.PostUiState

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