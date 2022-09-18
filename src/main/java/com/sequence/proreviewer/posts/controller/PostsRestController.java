package com.sequence.proreviewer.posts.controller;

import com.sequence.proreviewer.posts.infra.repository.PostsRepository;
import com.sequence.proreviewer.posts.dto.PostsRequestDto;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class PostsRestController {
    private PostsRepository postsRepository;

    @GetMapping("/posts") //전체 글 조회
    public String main() {
    }


    @PostMapping("/posts/write") //글 작성
    public void savePosts(@RequestBody PostsRequestDto dto){
        postsRepository.savePosts(dto.toEntity());
    }

}
