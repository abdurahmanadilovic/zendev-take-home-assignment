package com.example.postsviewer

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.postsviewer.presentation.PostUiState
import com.example.postsviewer.presentation.PostViewModel
import com.example.postsviewer.ui.components.PostCard
import com.example.postsviewer.ui.components.PostsListHeader
import com.example.postsviewer.ui.theme.PostsViewerTheme
import kotlinx.serialization.Serializable
import org.koin.androidx.compose.koinViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PostsViewerTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    MyAppNavHost(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@Serializable
object Home

@Serializable
data class PostDetailsRoute(val id: Int)

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
                        item {
                            PostsListHeader(uiState.posts.size)
                        }
                        items(uiState.posts.size) { index ->
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

@Composable
fun PostDetailsScreen(postId: Int) {
    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text("Details for Post ID: $postId")
    }
}

@Composable
fun MyAppNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = Home
    ) {
        composable<Home> {
            val viewModel: PostViewModel = koinViewModel()

            PostListScreen(
                viewModel = viewModel,
                onNavigateToPostDetails = { postId ->
                    navController.navigate(PostDetailsRoute(id = postId))
                }
            )
        }

        composable<PostDetailsRoute> { backStackEntry ->
            val route: PostDetailsRoute = backStackEntry.toRoute()
            PostDetailsScreen(postId = route.id)
        }
    }
}