package com.example.postsviewer.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.postsviewer.presentation.PostUiState
import com.example.postsviewer.presentation.PostViewModel

@Composable
fun PostListScreen(
    viewModel: PostViewModel,
    onNavigateToPostDetails: (postId: Int) -> Unit
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    Box(modifier = Modifier.fillMaxSize()) {
        when (val uiState = state) {
            is PostUiState.Loading -> CircularProgressIndicator(Modifier.align(Alignment.Center))
            is PostUiState.Success -> {
                Column {
                    LazyColumn {
                        item(key = "header") {
                            PostsListHeader(uiState.posts.size)
                        }
                        items(
                            uiState.posts.size,
                            key = { index ->
                                uiState.posts[index].id
                            }, contentType = { "post_card_type" }) { index ->
                            val post = uiState.posts[index]
                            PostCard(post, onClick = {
                                onNavigateToPostDetails(post.id)
                            })
                        }
                    }
                }
            }

            is PostUiState.Error -> Text("Error: ${uiState.message}")
        }
    }
}
