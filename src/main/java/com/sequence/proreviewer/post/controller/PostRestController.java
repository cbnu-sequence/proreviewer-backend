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
    public PostResponseDto findById(@PathVariable Long id){
        return postService.findById(id);
    }

    @PostMapping("/write") //글 작성
    public void savePost(@RequestBody PostRequestDto dto){
        postService.write(dto);
    }

    @PutMapping("/edit/{id}") //게시글 수정
    public void editPost(@PathVariable Long id, @RequestBody PostUpdateDto postUpdateDto){
        postService.editPost(id, postUpdateDto);
    }

    @DeleteMapping("/delete/{id}") //게시글 삭제
    public void deletePost(@PathVariable Long id){
        postService.deletePost(id);
    }

    @GetMapping("/search")
    public List<PostResponseDto> searchPost(@RequestParam("type") int type,
                                            @RequestParam("keyword") String keyword){
        return postService.searchPost(type, keyword);
    }
}
