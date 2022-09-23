package com.sequence.proreviewer.post.infra.repository.jpa;

import com.sequence.proreviewer.post.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.stream.Stream;

public interface JpaPostRepository extends JpaRepository<Post, Long> {
    @Query("SELECT p " +
            "FROM Post p " +
            "ORDER BY p.id DESC")
    Stream<Post> findAllDesc();

    Stream<Post> findByTitleContaining(String keyword);

    Stream<Post> findByBodyContaining(String keyword);

    Stream<Post>findByUserNameContaining(String keyword);
}
