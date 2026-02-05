package com.example.postsviewer.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.postsviewer.R
import com.example.postsviewer.presentation.PostUiState
import com.example.postsviewer.presentation.PostViewModel
import com.example.postsviewer.ui.components.PostCard
import org.koin.androidx.compose.koinViewModel

@Composable
fun PostListScreen(
    onNavigateToPostDetails: (postId: Int) -> Unit,
    viewModel: PostViewModel = koinViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    PostListContent(
        uiState = uiState,
        onPostClick = onNavigateToPostDetails,
        onRetry = { viewModel.loadPosts() }
    )
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3Api::class)
@Composable
fun PostListContent(
    uiState: PostUiState,
    onPostClick: (Int) -> Unit,
    onRetry: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(title = { Text(stringResource(R.string.trending_posts)) })
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            when (uiState) {
                is PostUiState.Loading -> {
                    CircularProgressIndicator(Modifier.align(Alignment.Center))
                }

                is PostUiState.Success -> {
                    LazyColumn(modifier = Modifier.fillMaxSize()) {
                        items(
                            count = uiState.posts.size,
                            key = { index -> uiState.posts[index].id },
                            contentType = { "post_card" }
                        ) { index ->
                            val post = uiState.posts[index]
                            PostCard(
                                post = post,
                                onClick = { onPostClick(post.id) }
                            )
                        }
                    }
                }

                is PostUiState.Error -> {
                    Column(
                        modifier = Modifier.align(Alignment.Center),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            modifier = Modifier.padding(8.dp),
                            text = uiState.message.asString(),
                            color = MaterialTheme.colorScheme.error
                        )
                        Button(onClick = onRetry) { Text(stringResource(R.string.retry)) }
                    }
                }
            }
        }
    }
}
