package com.example.postsviewer

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.postsviewer.ui.theme.PostsViewerTheme
import kotlinx.serialization.Serializable

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

@Composable
fun PostListScreen(onNavigateToPostDetails: (postId: Int) -> Unit) {

}

@Composable
fun PostDetailsScreen() {

}

@Serializable
object Home

@Serializable
data class PostDetailsScreen(val id: Int)

@Composable
fun MyAppNavHost(
    modifier: Modifier,
    navController: NavHostController = rememberNavController(),
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = Home
    ) {
        composable<PostDetailsScreen> {
            PostListScreen(
                onNavigateToPostDetails = { postId -> navController.navigate(route = PostDetailsScreen(id = postId)) },
                /*...*/
            )
        }
        composable<PostDetailsScreen> { PostDetailsScreen(/*...*/) }
    }
}