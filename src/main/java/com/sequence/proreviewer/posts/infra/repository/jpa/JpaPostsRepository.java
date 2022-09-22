package com.sequence.proreviewer.posts.infra.repository.jpa;

import com.sequence.proreviewer.posts.domain.Posts;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

public interface JpaPostsRepository extends JpaRepository<Posts, Long> {

    @Query("SELECT p " +
            "FROM Posts p " +
            "ORDER BY p.id DESC")
    Stream<Posts> findAllDesc();

    Stream<Posts> findByTitleContaining(String keyword);

    Stream<Posts> findByBodyContaining(String keyword);

    Stream<Posts>findByUserNameContaining(String keyword);
}
