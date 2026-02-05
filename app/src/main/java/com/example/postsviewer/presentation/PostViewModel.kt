package com.example.postsviewer.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.postsviewer.data.Post
import com.example.postsviewer.domain.GetPostsUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class PostViewModel(
    private val getPostsUseCase: GetPostsUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<PostUiState>(PostUiState.Loading)
    val uiState: StateFlow<PostUiState> = _uiState.asStateFlow()

    init {
        loadPosts()
    }

    fun loadPosts() {
        viewModelScope.launch {
            _uiState.value = PostUiState.Loading
            getPostsUseCase()
                .onSuccess { posts -> _uiState.value = PostUiState.Success(posts) }
                .onFailure { error -> _uiState.value = PostUiState.Error(error.message ?: "Unknown Error") }
        }
    }
}

sealed interface PostUiState {
    object Loading : PostUiState
    data class Success(val posts: List<Post>) : PostUiState
    data class Error(val message: String) : PostUiState
}