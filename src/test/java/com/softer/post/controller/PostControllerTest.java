package com.softer.post.controller;

import com.softer.post.model.Post;
import com.softer.post.service.PostService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(PostController.class)
@AutoConfigureMockMvc
class PostControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockitoBean
    PostService postService;

    private List<Post> posts;

    @BeforeEach
    void setUp() {
        posts = List.of(
                new Post(1, 1, "Post Title 1", "Hello world 1", null),
                new Post(2, 1, "Post Title 2", "Hello world 2", null)
        );
    }

    @Test
    void shouldFindAllPosts() throws Exception {
        when(postService.findAll()).thenReturn(posts);

        mockMvc.perform(get("/api/posts"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[1].title").value("Post Title 2"));
    }

    @Test
    void shouldFindPostWhenGivenValidID() throws Exception {
        when(postService.findById(1)).thenReturn(posts.getFirst());

        mockMvc.perform(get("/api/posts/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.userId").value(1))
                .andExpect(jsonPath("$.title").value("Post Title 1"))
                .andExpect(jsonPath("$.body").value("Hello world 1"));
    }

    @Test
    void shouldNotFindPostWhenGivenInvalidID() throws Exception {
        when(postService.findById(999))
                .thenThrow(new com.softer.post.exception.PostNoFoundException());

        mockMvc.perform(get("/api/posts/999"))
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldCreateNewPostWhenPostDataIsValid() throws Exception {
        Post post = new Post(3, 1, "Post Title 3", "Hello world 3", null);
        when(postService.create(any(Post.class))).thenReturn(post);

        mockMvc.perform(post("/api/posts")
                        .contentType(APPLICATION_JSON)
                        .content("""
                                {"id":3,"userId":1,"title":"Post Title 3","body":"Hello world 3","version":null}
                                """))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(3));
    }

    @Test
    void shouldNotCreateNewPostWhenPostDataIsInvalid() throws Exception {
        mockMvc.perform(post("/api/posts")
                        .contentType(APPLICATION_JSON)
                        .content("""
                                {"id":4,"userId":1,"title":"","body":"","version":null}
                                """))
                .andExpect(status().isBadRequest());

        verify(postService, never()).create(any(Post.class));
    }

    @Test
    void shouldUpdatePostWhenGivenValidPostData() throws Exception {
        Post updated = new Post(1, 1, "Updated Title", "Updated Body", 1);
        when(postService.update(eq(1), any(Post.class))).thenReturn(updated);

        mockMvc.perform(put("/api/posts/1")
                        .contentType(APPLICATION_JSON)
                        .content("""
                                {"id":1,"userId":1,"title":"Updated Title","body":"Updated Body","version":1}
                                """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Updated Title"));
    }

    @Test
    void shouldDeletePostWhenGivenValidID() throws Exception {
        doNothing().when(postService).delete(1);

        mockMvc.perform(delete("/api/posts/1"))
                .andExpect(status().isNoContent());

        verify(postService, times(1)).delete(1);
    }
}
