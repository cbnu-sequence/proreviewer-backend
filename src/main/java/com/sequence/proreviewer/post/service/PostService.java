package com.sequence.proreviewer.post.service;

import com.sequence.proreviewer.post.common.exception.PostNotFoundException;
import com.sequence.proreviewer.post.domain.Post;
import com.sequence.proreviewer.post.dto.PostUpdateDto;
import com.sequence.proreviewer.post.dto.PostResponseDto;
import com.sequence.proreviewer.post.infra.repository.PostRepository;
import com.sequence.proreviewer.post.dto.PostRequestDto;
import com.sequence.proreviewer.user.application.exception.UserNotFoundException;
import com.sequence.proreviewer.user.domain.User;
import com.sequence.proreviewer.user.domain.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@AllArgsConstructor
@Service
public class PostService {
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    @Transactional  //post 작성
    public Long write(PostRequestDto postRequestDto){

        User user = userRepository.findById(postRequestDto.getUser_id()).orElseThrow(UserNotFoundException::new); //작성자 정보

        postRequestDto.setUser(user);

        Post post = postRequestDto.toEntity();

        postRepository.savePost(post);

        return (post.getId());
    }

    //모든 포스트 조회
    @Transactional(readOnly = true)
    public List<PostResponseDto> getAllPosts() {

        List<PostResponseDto> postResponseDtoList;

        try(Stream<Post> postStream = postRepository.getAllPosts()){
            postResponseDtoList = postStream
                    .map(PostResponseDto::new)
                    .collect(Collectors.toList());
        }

        return postResponseDtoList;
    }

    //포스트 id로 포스트 조회
    @Transactional(readOnly = true)
    public PostResponseDto findById(Long id){
        Post post = postRepository.findById(id).orElseThrow(PostNotFoundException::new);

        return PostResponseDto.builder()
                .entity(post)
                .build();
    }

    @Transactional //포스트 수정
    public void editPost(Long id, PostUpdateDto postUpdateDto) {
        Optional<Post> optionalPost = postRepository.findById(id);

        optionalPost.ifPresentOrElse(
                post -> {
                    post.updatePost(postUpdateDto);
                    },
                ()->{
                    throw new PostNotFoundException();
                }
        );
    }

    @Transactional //포스트 삭제
    public void deletePost(Long id){
        Optional<Post> optionalPost = postRepository.findById(id);

        optionalPost.ifPresentOrElse(
                post -> {
                    postRepository.deletePost(id);
                },
                    ()->{
                        throw new PostNotFoundException();
                    }
        );
    }

    //포스트 검색
    @Transactional(readOnly = true)
    public List<PostResponseDto> searchPost(int type, String keyword){
                                    //type : 1 제목, 2 본문, 3 작성자

        if(type==1){
            try(Stream<Post> postStream = postRepository.findByTitleContaining(keyword)){
                return postStream
                        .map(PostResponseDto::new)
                        .collect(Collectors.toList());
            }
        }
        else if(type==2){
            try(Stream<Post> postStream = postRepository.findByBodyContaining(keyword)){
                return postStream
                        .map(PostResponseDto::new)
                        .collect(Collectors.toList());
            }
        }
        else if(type==3){
            try(Stream<Post> postStream = postRepository.findByUsernameContaining(keyword)){
                return postStream
                        .map(PostResponseDto::new)
                        .collect(Collectors.toList());
            }
        }
        else{
            throw new IllegalArgumentException();
        }
    }
}
