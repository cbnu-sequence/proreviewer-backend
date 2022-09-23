package com.sequence.proreviewer.post.controller;

import com.sequence.proreviewer.post.dto.PostUpdateDto;
import com.sequence.proreviewer.post.dto.PostResponseDto;
import com.sequence.proreviewer.post.dto.PostRequestDto;
import com.sequence.proreviewer.post.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/posts")
public class PostRestController {
    private final PostService postService;

    @GetMapping//모든 포스트 조회
    public List<PostResponseDto> getAllPosts(){
        return postService.getAllPosts();
    }

    @GetMapping("/{id}") //게시글 id로 게시글 조회
    public PostResponseDto findById(@PathVariable Long id /*로그인 유저 정보*/){
        PostResponseDto postResponseDto = postService.findById(id);

//        if(user.getId()==postResponseDto.getId()){
//            postResponseDto.setWriter(true);
//        } //게시글 조회시 유저 정보 가져와서 작성자이면 true로 변경

        return postResponseDto;
    }

    @PostMapping("/write") //글 작성
    public void savePost(@RequestBody PostRequestDto dto /*로그인 유저 정보*/){
        postService.write(dto, "email"); //email 주소 입력
    }

    @PutMapping("/edit/{id}") //게시글 수정
    public void editPost(@PathVariable Long id, @RequestBody PostUpdateDto postUpdateDto /*, 로그인 유저 정보*/){
        postUpdateDto.setEmail("email"); //현재 로그인된 유저 이메일 주소
        postService.editPost(id, postUpdateDto);
    }

    @DeleteMapping("/delete/{id}") //게시글 삭제
    public void deletePost(@PathVariable Long id /*, 로그인 유저 정보*/){
        postService.deletePost(id, "email"); //email 주소 입력
    }

    @GetMapping("/search")
    public List<PostResponseDto> searchPost(@RequestParam("type") int type,
                                            @RequestParam("keyword") String keyword){
        return postService.searchPost(type, keyword);
    }
}
