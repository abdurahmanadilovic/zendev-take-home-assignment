package com.example.postsviewer.di

import com.example.postsviewer.data.PostApi
import com.example.postsviewer.data.PostRepository
import com.example.postsviewer.data.PostRepositoryImpl
import com.example.postsviewer.domain.GetPostByIdUseCase
import com.example.postsviewer.domain.GetPostsUseCase
import com.example.postsviewer.presentation.PostDetailsViewModel
import com.example.postsviewer.presentation.PostViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import org.koin.core.module.dsl.viewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory

val appModule = module {
    single {
        Retrofit.Builder()
            .baseUrl(PostApi.BACKEND_URL)
            .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
            .build()
            .create(PostApi::class.java)
    }

    single<PostRepository> { PostRepositoryImpl(get()) }

    single { GetPostsUseCase(Dispatchers.Default, get()) }

    factory { GetPostByIdUseCase(get()) }

    viewModelOf(::PostViewModel)

    viewModel { (postId: Int) -> PostDetailsViewModel(postId, get()) }
}