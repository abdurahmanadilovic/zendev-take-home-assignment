package com.example.postsviewer.ui.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.postsviewer.presentation.PostViewModel
import kotlinx.serialization.Serializable
import org.koin.androidx.compose.koinViewModel

@Serializable
object Home

@Serializable
data class PostDetailsRoute(val id: Int)

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
            PostDetailsScreen(postId = route.id, onBackClick = {
                navController.navigateUp()
            })
        }
    }
}
