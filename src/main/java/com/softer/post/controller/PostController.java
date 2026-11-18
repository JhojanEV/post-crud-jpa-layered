package com.softer.post.controller;

import com.softer.post.model.Post;
import com.softer.post.service.PostService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/posts")
public class PostController {

    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping
    public List<Post> findAll() {
        return postService.findAll();
    }

    @GetMapping("/{id}")
    public Post findById(@PathVariable Integer id) {
        return postService.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Post create(@RequestBody @Valid Post post) {
        return postService.create(post);
    }

    @PutMapping("/{id}")
    public Post update(@PathVariable Integer id, @RequestBody Post post) {
        return postService.update(id, post);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Integer id) {
        postService.delete(id);
    }
}
