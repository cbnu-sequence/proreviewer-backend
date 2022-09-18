package com.sequence.proreviewer.posts.service;

import com.sequence.proreviewer.posts.domain.Posts;
import com.sequence.proreviewer.posts.dto.PostsResponseDto;
import com.sequence.proreviewer.posts.infra.repository.PostsRepository;
import com.sequence.proreviewer.posts.dto.PostsRequestDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class PostsService {
    private PostsRepository postsRepository;

    @Transactional  //post 작성
    public Long write(PostsRequestDto postsRequestDto){
        Posts posts = postsRequestDto.toEntity();

        postsRepository.savePosts(posts);

        return (posts.getId());
    }

    @Transactional
    public List<PostsResponseDto> getAllPosts() { //모든 포스트 조회
        return postsRepository.getAllPosts()
                .map(PostsResponseDto::new)
                .collect(Collectors.toList());
    }

}
