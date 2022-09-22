package com.sequence.proreviewer.posts.infra.repository.jpa.impl;

import com.sequence.proreviewer.posts.domain.Posts;
import com.sequence.proreviewer.posts.infra.repository.PostsRepository;
import com.sequence.proreviewer.posts.infra.repository.jpa.JpaPostsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

@Repository
@RequiredArgsConstructor
public class PostsRepositoryImpl implements PostsRepository {

    private final JpaPostsRepository postsRepository;

    @Override
    public void savePosts(Posts posts) {
        postsRepository.save(posts);
    }

    @Override
    public void deleteAllPosts() {
        postsRepository.deleteAll();
    }

    @Override
    public Stream<Posts> getAllPosts() {
        return postsRepository.findAllDesc();
    }

    @Override
    public Optional<Posts> findById(Long id) {
        return postsRepository.findById(id);
    }

    @Override
    public void deletePosts(Long id) {
        postsRepository.delete(postsRepository.findById(id).orElseThrow(IllegalArgumentException::new));
    }

    @Override
    public Stream<Posts> findByTitleContaining(String keyword) {
        return postsRepository.findByTitleContaining(keyword);
    }

    @Override
    public Stream<Posts> findByBodyContaining(String keyword) {
        return postsRepository.findByBodyContaining(keyword);
    }

    @Override
    public Stream<Posts> findByUsernameContaining(String keyword) {
        return postsRepository.findByUserNameContaining(keyword);
    }
}
