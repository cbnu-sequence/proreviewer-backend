package com.sequence.proreviewer.posts.controller;

import com.sequence.proreviewer.posts.dto.PostsResponseDto;
import com.sequence.proreviewer.posts.dto.PostsRequestDto;
import com.sequence.proreviewer.posts.service.PostsService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@AllArgsConstructor
public class PostsRestController {
    private PostsService postsService;

    @GetMapping("/posts") //모든 포스트 조회
    public List<PostsResponseDto> getAllPosts(){
        return postsService.getAllPosts();
    }

    @GetMapping("/posts/{id}") //게시글 id로 게시글 조회
    public PostsResponseDto findById(@PathVariable Long id){
        return postsService.findById(id);
    }

    @PostMapping("/posts/write") //글 작성
    public void savePosts(@RequestBody PostsRequestDto dto){
        postsService.write(dto);
    }

    @PutMapping("posts/edit/{id}") //게시글 수정
    public void editPosts(@PathVariable Long id, @RequestBody Map<String, String> map){
        postsService.editPosts(id, map.get("title"), map.get("body"));
    }

    @DeleteMapping("posts/delete/{id}") //게시글 삭제
    public void deletePosts(@PathVariable Long id){
        postsService.deletePosts(id);
    }

    @GetMapping("/search")
    public List<PostsResponseDto> searchPost(@RequestParam("type") int type,
                                             @RequestParam("keyword") String keyword){
        return postsService.searchPost(type, keyword);
    }
}
