package com.sequence.proreviewer.post.controller;

import com.sequence.proreviewer.post.dto.PostRequestDto;
import com.sequence.proreviewer.post.dto.PostResponseDto;
import com.sequence.proreviewer.post.dto.PostUpdateDto;
import com.sequence.proreviewer.post.service.PostService;
import com.sequence.proreviewer.post.common.searchType.SearchType;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/posts")
public class PostController {
    private final PostService postService;

    @GetMapping
    public List<PostResponseDto> findAll() {
        return postService.findAll();
    }

    @GetMapping("/{id}")
    public PostResponseDto findById(@PathVariable Long id) {
        return postService.findById(id);
    }

    @PostMapping("/write")
    public void save(@RequestBody PostRequestDto dto) {
        postService.write(dto, 0L); //추후에 spring security를 통해 userId를 가져올 예정입니다
    }

    @PutMapping("/edit/{id}")
    public void edit(@PathVariable Long id, @RequestBody PostUpdateDto postUpdateDto) {
        postService.edit(id, postUpdateDto);
    }

    @DeleteMapping("/delete/{id}")
    public void delete(@PathVariable Long id) {
        postService.delete(id);
    }

    @GetMapping("/search")
    public List<PostResponseDto> search(@RequestParam("type") SearchType type,
                                        @RequestParam("keyword") String keyword) {
        return postService.search(type, keyword);
    }
}
