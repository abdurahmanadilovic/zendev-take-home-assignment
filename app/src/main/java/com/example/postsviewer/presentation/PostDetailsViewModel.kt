package com.example.postsviewer.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.postsviewer.data.Post
import com.example.postsviewer.domain.GetPostByIdUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class PostDetailsViewModel(
    private val postId: Int,
    private val getPostByIdUseCase: GetPostByIdUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<DetailsUiState>(DetailsUiState.Loading)
    val uiState: StateFlow<DetailsUiState> = _uiState.asStateFlow()

    init {
        loadPost()
    }

    private fun loadPost() {
        viewModelScope.launch {
            getPostByIdUseCase(postId)
                .onSuccess { post -> _uiState.value = DetailsUiState.Success(post) }
                .onFailure { _uiState.value = DetailsUiState.Error("Failed to load post") }
        }
    }
}

sealed interface DetailsUiState {
    object Loading : DetailsUiState
    data class Success(val post: Post) : DetailsUiState
    data class Error(val message: String) : DetailsUiState
}