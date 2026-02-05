package com.example.postsviewer

import com.example.postsviewer.data.Post
import com.example.postsviewer.domain.GetPostByIdUseCase
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class GetPostByIdUseCaseTest {

    private lateinit var fakeRepository: FakePostRepository
    private lateinit var useCase: GetPostByIdUseCase

    @Before
    fun setUp() {
        fakeRepository = FakePostRepository()
        useCase = GetPostByIdUseCase(fakeRepository)
    }

    @Test
    fun `invoke returns success with post when repository finds post`() = runTest {
        // Given
        val expectedPost = Post(id = 1, title = "Test Post", body = "Test Body", userId = 1)
        fakeRepository.postToReturn = expectedPost

        // When
        val result = useCase(1)

        // Then
        assertTrue(result.isSuccess)
        assertEquals(expectedPost, result.getOrNull())
    }

    @Test
    fun `invoke returns post with correct id`() = runTest {
        // Given
        val expectedPost = Post(id = 42, title = "Post 42", body = "Body 42", userId = 5)
        fakeRepository.postToReturn = expectedPost

        // When
        val result = useCase(42)

        // Then
        assertTrue(result.isSuccess)
        assertEquals(42, result.getOrNull()?.id)
    }

    @Test
    fun `invoke returns failure when repository throws exception`() = runTest {
        // Given
        val expectedException = RuntimeException("Network error")
        fakeRepository.exceptionToThrow = expectedException

        // When
        val result = useCase(1)

        // Then
        assertTrue(result.isFailure)
        assertEquals(expectedException, result.exceptionOrNull())
    }

    @Test
    fun `invoke returns failure when post not found`() = runTest {
        // Given - postToReturn is null, so getPostById will throw NoSuchElementException
        fakeRepository.postToReturn = null

        // When
        val result = useCase(999)

        // Then
        assertTrue(result.isFailure)
        assertTrue(result.exceptionOrNull() is NoSuchElementException)
    }

    @Test
    fun `invoke returns post with all properties intact`() = runTest {
        // Given
        val expectedPost = Post(
            id = 10,
            title = "Complete Title",
            body = "Complete Body Content",
            userId = 7
        )
        fakeRepository.postToReturn = expectedPost

        // When
        val result = useCase(10)

        // Then
        assertTrue(result.isSuccess)
        val post = result.getOrNull()!!
        assertEquals(10, post.id)
        assertEquals("Complete Title", post.title)
        assertEquals("Complete Body Content", post.body)
        assertEquals(7, post.userId)
    }

    @Test
    fun `invoke handles post with empty strings`() = runTest {
        // Given
        val expectedPost = Post(id = 1, title = "", body = "", userId = 1)
        fakeRepository.postToReturn = expectedPost

        // When
        val result = useCase(1)

        // Then
        assertTrue(result.isSuccess)
        assertEquals("", result.getOrNull()?.title)
        assertEquals("", result.getOrNull()?.body)
    }
}
