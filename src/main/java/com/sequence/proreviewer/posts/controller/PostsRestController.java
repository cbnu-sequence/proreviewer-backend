package com.sequence.proreviewer.posts.controller;

import com.sequence.proreviewer.posts.dto.PostUpdateDto;
import com.sequence.proreviewer.posts.dto.PostsResponseDto;
import com.sequence.proreviewer.posts.dto.PostsRequestDto;
import com.sequence.proreviewer.posts.service.PostsService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/posts")
public class PostsRestController {
    private final PostsService postsService;

    @GetMapping//모든 포스트 조회
    public List<PostsResponseDto> getAllPosts(){
        return postsService.getAllPosts();
    }

    @GetMapping("/{id}") //게시글 id로 게시글 조회
    public PostsResponseDto findById(@PathVariable Long id /*로그인 유저 정보*/){
        PostsResponseDto postsResponseDto = postsService.findById(id);

//        if(user.getId()==postsResponseDto.getId()){
//            postsResponseDto.setWriter(true);
//        } //게시글 조회시 유저 정보 가져와서 작성자이면 true로 변경

        return postsResponseDto;
    }

    @PostMapping("/write") //글 작성
    public void savePosts(@RequestBody PostsRequestDto dto /*로그인 유저 정보*/){
        postsService.write(dto, "email"); //email 주소 입력
    }

    @PutMapping("/edit/{id}") //게시글 수정
    public void editPosts(@PathVariable Long id, @RequestBody PostUpdateDto postUpdateDto /*, 로그인 유저 정보*/){
        postUpdateDto.setEmail("email"); //현재 로그인된 유저 이메일 주소
        postsService.editPosts(id, postUpdateDto);
    }

    @DeleteMapping("/delete/{id}") //게시글 삭제
    public void deletePosts(@PathVariable Long id /*, 로그인 유저 정보*/){
        postsService.deletePosts(id, "email"); //email 주소 입력
    }
}
