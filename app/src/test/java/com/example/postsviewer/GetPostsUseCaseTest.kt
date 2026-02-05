package com.example.postsviewer

import com.example.postsviewer.data.Post
import com.example.postsviewer.domain.GetPostsUseCase
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class GetPostsUseCaseTest {

    private val testDispatcher = StandardTestDispatcher()
    private lateinit var fakeRepository: FakePostRepository
    private lateinit var useCase: GetPostsUseCase

    @Before
    fun setUp() {
        fakeRepository = FakePostRepository()
        useCase = GetPostsUseCase(testDispatcher, fakeRepository)
    }

    @Test
    fun `invoke returns success with filtered posts when repository succeeds`() = runTest(testDispatcher) {
        // Given
        val posts = listOf(
            Post(id = 1, title = "Post 1", body = "Body 1", userId = 1),
            Post(id = 49, title = "Post 49", body = "Body 49", userId = 1),
            Post(id = 50, title = "Post 50", body = "Body 50", userId = 1),
            Post(id = 100, title = "Post 100", body = "Body 100", userId = 1)
        )
        fakeRepository.postsToReturn = posts

        // When
        val result = useCase()

        // Then
        assertTrue(result.isSuccess)
        val filteredPosts = result.getOrNull()!!
        assertEquals(2, filteredPosts.size)
        assertTrue(filteredPosts.all { it.id < 50 })
        assertEquals(listOf(1, 49), filteredPosts.map { it.id })
    }

    @Test
    fun `invoke filters out posts with id equal to or greater than 50`() = runTest(testDispatcher) {
        // Given
        val posts = (1..100).map { id ->
            Post(id = id, title = "Post $id", body = "Body $id", userId = 1)
        }
        fakeRepository.postsToReturn = posts

        // When
        val result = useCase()

        // Then
        assertTrue(result.isSuccess)
        val filteredPosts = result.getOrNull()!!
        assertEquals(49, filteredPosts.size)
        assertTrue(filteredPosts.all { it.id < 50 })
    }

    @Test
    fun `invoke returns empty list when all posts have id greater than or equal to 50`() = runTest(testDispatcher) {
        // Given
        val posts = listOf(
            Post(id = 50, title = "Post 50", body = "Body 50", userId = 1),
            Post(id = 51, title = "Post 51", body = "Body 51", userId = 1)
        )
        fakeRepository.postsToReturn = posts

        // When
        val result = useCase()

        // Then
        assertTrue(result.isSuccess)
        assertEquals(emptyList<Post>(), result.getOrNull())
    }

    @Test
    fun `invoke returns empty list when repository returns empty list`() = runTest(testDispatcher) {
        // Given
        fakeRepository.postsToReturn = emptyList()

        // When
        val result = useCase()

        // Then
        assertTrue(result.isSuccess)
        assertEquals(emptyList<Post>(), result.getOrNull())
    }

    @Test
    fun `invoke returns failure when repository throws exception`() = runTest(testDispatcher) {
        // Given
        val expectedException = RuntimeException("Network error")
        fakeRepository.exceptionToThrow = expectedException

        // When
        val result = useCase()

        // Then
        assertTrue(result.isFailure)
        assertEquals(expectedException, result.exceptionOrNull())
    }

    @Test
    fun `invoke preserves post order from repository`() = runTest(testDispatcher) {
        // Given
        val posts = listOf(
            Post(id = 30, title = "Post 30", body = "Body 30", userId = 1),
            Post(id = 10, title = "Post 10", body = "Body 10", userId = 1),
            Post(id = 20, title = "Post 20", body = "Body 20", userId = 1)
        )
        fakeRepository.postsToReturn = posts

        // When
        val result = useCase()

        // Then
        assertTrue(result.isSuccess)
        val filteredPosts = result.getOrNull()!!
        assertEquals(listOf(30, 10, 20), filteredPosts.map { it.id })
    }
}
