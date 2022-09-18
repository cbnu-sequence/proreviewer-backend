package com.sequence.proreviewer.posts.infra.repository;

import com.sequence.proreviewer.posts.domain.Posts;

import java.util.stream.Stream;

public interface PostsRepository {
    void savePosts(Posts posts);
    void deleteAllPosts();

    Stream<Posts> getAllPosts();
}