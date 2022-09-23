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

    private final JpaPostRepository postsRepository;

    @Override
    public void savePost(Post post) {
        postsRepository.save(post);
    }

    @Override
    public Stream<Post> getAllPosts() {
        return postsRepository.findAllDesc();
    }

    @Override
    public void deletePost(Long id) {
        postsRepository.delete(postsRepository.findById(id).orElseThrow(IllegalArgumentException::new));
    }

    @Override
    public Optional<Post> findById(Long id) {
        return postsRepository.findById(id);
    }

    @Override
    public Stream<Post> findByTitleContaining(String keyword) {
        return postsRepository.findByTitleContaining(keyword);
    }

    @Override
    public Stream<Post> findByBodyContaining(String keyword) {
        return postsRepository.findByBodyContaining(keyword);
    }

    @Override
    public Stream<Post> findByUsernameContaining(String keyword) {
        return postsRepository.findByUserNameContaining(keyword);
    }
}
