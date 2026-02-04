package com.example.postsviewer.di

import com.example.postsviewer.data.PostApi
import com.example.postsviewer.data.PostRepository
import com.example.postsviewer.data.PostRepositoryImpl
import com.example.postsviewer.domain.GetFilteredPostsUseCase
import com.example.postsviewer.presentation.PostViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module
import retrofit2.Retrofit

val appModule = module {
    single {
        Retrofit.Builder()
            .baseUrl("https://jsonplaceholder.typicode.com/")
            .build()
            .create(PostApi::class.java)
    }

    single<PostRepository> { PostRepositoryImpl(get()) }

    factory { GetFilteredPostsUseCase(get()) }

    viewModelOf(::PostViewModel)
}