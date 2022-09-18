package com.sequence.proreviewer.posts.infra.repository;

import com.sequence.proreviewer.posts.domain.Posts;
import com.sequence.proreviewer.posts.infra.repository.jpa.JpaPostsRepository;
import org.junit.After;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PostsRepositoryTest {

    @Autowired
    JpaPostsRepository postsRepository;

    @After
    public void cleanup() {
        postsRepository.deleteAll();
    }

    @Test
    public void cteateAndGet() {
        postsRepository.save(Posts.builder()
                .title("테스트 게시글")
                .body("테스트 본문")
                .user_name("123")
                .build());

        List<Posts> postsList = postsRepository.findAll();

        Posts posts = postsList.get(0);
        assertThat(posts.getTitle(), is("테스트 게시글"));
        assertThat(posts.getBody(), is("테스트 본문"));

    }

    @Test
    public void BaseTimeEntityTest(){
        LocalDateTime now = LocalDateTime.now();
        postsRepository.save(Posts.builder()
                        .title("테스트 타이틀")
                        .body("본문본문")
                        .user_name("Test")
                .build());

        List<Posts> postsList = postsRepository.findAll();

        Posts posts = postsList.get(0);
        assertTrue(posts.getCreated_at().isAfter(now));
        assertTrue(posts.getModified_at().isAfter(now));

    }
}