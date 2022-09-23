package com.sequence.proreviewer.post.infra.repository;

import com.sequence.proreviewer.post.domain.Post;

import java.util.Optional;
import java.util.stream.Stream;

public interface PostRepository {
    void savePost(Post post);

    Stream<Post> getAllPosts();

    Optional<Post> findById(Long id);

    void deletePost(Long id);

    Stream<Post> findByTitleContaining(String keyword);

    Stream<Post> findByBodyContaining(String keyword);

    Stream<Post> findByUsernameContaining(String keyword);
}
