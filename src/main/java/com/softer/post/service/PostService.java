package com.softer.post.service;

import com.softer.post.model.Post;
import com.softer.post.exception.PostNoFoundException;
import com.softer.post.repository.PostRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class PostService {

    private final PostRepository postRepository;

    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    @Transactional(readOnly = true)
    public List<Post> findAll() {
        return postRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Post findById(Integer id) {
        return postRepository.findById(id).orElseThrow(PostNoFoundException::new);
    }

    public Post create(Post post) {
        return postRepository.save(post);
    }

    public Post update(Integer id, Post post) {
        Post existing = postRepository.findById(id)
                .orElseThrow(PostNoFoundException::new);
        existing.updateContent(post.getTitle(), post.getBody());
        return postRepository.save(existing);
    }

    public void delete(Integer id) {
        postRepository.deleteById(id);
    }
}
