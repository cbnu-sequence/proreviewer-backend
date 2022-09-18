package com.sequence.proreviewer.posts.infra.repository.jpa.impl;

import com.sequence.proreviewer.posts.domain.Posts;
import com.sequence.proreviewer.posts.infra.repository.PostsRepository;
import com.sequence.proreviewer.posts.infra.repository.jpa.JpaPostsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.stream.Stream;

@Repository
@RequiredArgsConstructor
public class PostsRepositoryImpl implements PostsRepository {

    JpaPostsRepository postsRepository;

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
}
