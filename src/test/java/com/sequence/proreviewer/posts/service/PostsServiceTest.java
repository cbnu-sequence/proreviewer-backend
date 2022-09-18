package com.sequence.proreviewer.posts.service;

import com.sequence.proreviewer.posts.domain.Posts;
import com.sequence.proreviewer.posts.infra.repository.jpa.JpaPostsRepository;
import com.sequence.proreviewer.posts.dto.PostsRequestDto;
import org.junit.After;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PostsServiceTest {
    @Autowired
    private PostsService postsService;

    @Autowired
    private JpaPostsRepository postsRepository;

    @After
    public void cleanUp(){
        postsRepository.deleteAll();
    }

    @Test
    public void writeTest(){
        PostsRequestDto dto = PostsRequestDto.builder()
                .title("service#write() 테스트")
                .body("본문")
                .user_name("Test")
                .build();

        postsService.write(dto);

        List<Posts> postsList = postsRepository.findAll();
        Posts posts = postsList.get(0);

        assertThat(posts.getTitle()).isEqualTo(dto.getTitle());
        assertThat(posts.getBody()).isEqualTo(dto.getBody());
        assertThat(posts.getUser_name()).isEqualTo(dto.getUser_name());

    }
}
