package com.sequence.proreviewer.post.infra.repository.jpa.impl;

import com.sequence.proreviewer.post.domain.Post;
import com.sequence.proreviewer.post.infra.repository.PostRepository;
import com.sequence.proreviewer.post.infra.repository.jpa.JpaPostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.stream.Stream;

@Repository
@RequiredArgsConstructor
public class PostRepositoryImpl implements PostRepository {

    private final JpaPostRepository postRepository;

    @Override
    public void save(Post post) {
        postRepository.save(post);
    }

    @Override
    public Stream<Post> findAll() {
        return postRepository.findAllDesc();
    }

    @Override
    public void deleteById(Long id) {
        Optional<Post> optionalPost = postRepository.findById(id);
        Post post = optionalPost.orElseThrow(IllegalArgumentException::new);

        postRepository.delete(post);
    }

    @Override
    public Optional<Post> findById(Long id) {
        return postRepository.findById(id);
    }

    @Override
    public Stream<Post> findByTitleContaining(String keyword) {
        return postRepository.findByTitleContaining(keyword);
    }

    @Override
    public Stream<Post> findByBodyContaining(String keyword) {
        return postRepository.findByBodyContaining(keyword);
    }

    @Override
    public Stream<Post> findByUserId(String keyword) {
        Long userId = Long.parseLong(keyword);
        return postRepository.findByUserId(userId);
    }
}
