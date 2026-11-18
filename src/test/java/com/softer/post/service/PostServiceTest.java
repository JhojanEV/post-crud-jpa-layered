package com.softer.post.service;

import com.softer.post.exception.PostNoFoundException;
import com.softer.post.model.Post;
import com.softer.post.repository.PostRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PostServiceTest {

    @Mock
    PostRepository postRepository;

    @InjectMocks
    PostService postService;

    private Post existingPost;

    @BeforeEach
    void setUp() {
        existingPost = new Post(1, 1, "Original title", "Original body", 0);
    }

    @Test
    void shouldReturnAllPosts() {
        when(postRepository.findAll()).thenReturn(List.of(existingPost));

        List<Post> result = postService.findAll();

        assertEquals(1, result.size());
        assertEquals("Original title", result.getFirst().getTitle());
    }

    @Test
    void shouldReturnPostWhenIdExists() {
        when(postRepository.findById(1)).thenReturn(Optional.of(existingPost));

        Post result = postService.findById(1);

        assertEquals(existingPost, result);
    }

    @Test
    void shouldThrowExceptionWhenPostDoesNotExist() {
        when(postRepository.findById(999)).thenReturn(Optional.empty());

        assertThrows(PostNoFoundException.class, () -> postService.findById(999));
    }

    @Test
    void shouldCreatePost() {
        Post newPost = new Post(2, 1, "New title", "New body", null);
        when(postRepository.save(newPost)).thenReturn(newPost);

        Post result = postService.create(newPost);

        assertEquals(newPost, result);
        verify(postRepository).save(newPost);
    }

    @Test
    void shouldUpdatePostContentAndPreserveUserId() {
        when(postRepository.findById(1)).thenReturn(Optional.of(existingPost));
        when(postRepository.save(existingPost)).thenReturn(existingPost);

        Post result = postService.update(1,
                new Post(1, 99, "Updated title", "Updated body", null));

        assertEquals(1, result.getUserId());
        assertEquals("Updated title", result.getTitle());
        assertEquals("Updated body", result.getBody());
        verify(postRepository).save(existingPost);
    }

    @Test
    void shouldThrowExceptionWhenUpdatingPostThatDoesNotExist() {
        when(postRepository.findById(999)).thenReturn(Optional.empty());

        assertThrows(PostNoFoundException.class,
                () -> postService.update(999, new Post(999, 1, "Title", "Body", null)));
        verify(postRepository, never()).save(any());
    }

    @Test
    void shouldDeletePost() {
        postService.delete(1);

        verify(postRepository).deleteById(1);
    }
}
