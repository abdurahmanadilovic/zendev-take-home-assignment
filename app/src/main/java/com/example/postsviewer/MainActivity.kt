package com.example.postsviewer

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.postsviewer.ui.components.MyAppNavHost
import com.example.postsviewer.ui.theme.PostsViewerTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PostsViewerTheme {
                MyAppNavHost()
            }
        }
    }
}
