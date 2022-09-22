package com.sequence.proreviewer.posts.infra.repository;

import com.sequence.proreviewer.posts.domain.Posts;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

public interface PostsRepository {
    void savePosts(Posts posts);

    void deleteAllPosts();

    Stream<Posts> getAllPosts();

    Optional<Posts> findById(Long id);

    void deletePosts(Long id);

    Stream<Posts> findByTitleContaining(String keyword);

    Stream<Posts> findByBodyContaining(String keyword);

    Stream<Posts> findByUsernameContaining(String keyword);
}
