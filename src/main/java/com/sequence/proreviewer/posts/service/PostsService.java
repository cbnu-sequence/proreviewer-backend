package com.sequence.proreviewer.posts.service;

import com.sequence.proreviewer.posts.common.exception.OperationNotAllowedException;
import com.sequence.proreviewer.posts.common.exception.PostNotFoundException;
import com.sequence.proreviewer.posts.domain.Posts;
import com.sequence.proreviewer.posts.dto.PostUpdateDto;
import com.sequence.proreviewer.posts.dto.PostsResponseDto;
import com.sequence.proreviewer.posts.infra.repository.PostsRepository;
import com.sequence.proreviewer.posts.dto.PostsRequestDto;
import com.sequence.proreviewer.user.application.exception.UserNotFoundException;
import com.sequence.proreviewer.user.domain.User;
import com.sequence.proreviewer.user.domain.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import javax.transaction.Transactional;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class PostsService {
    private final PostsRepository postsRepository;
    private final UserRepository userRepository;

    @Transactional  //post 작성
    public Long write(PostsRequestDto postsRequestDto, String email){

        User user = userRepository.findByEmail(email).orElseThrow(UserNotFoundException::new); //작성자 정보

        postsRequestDto.setUser(user);

        Posts posts = postsRequestDto.toEntity();

        postsRepository.savePosts(posts);

        return (posts.getId());
    }

    //모든 포스트 조회
    public List<PostsResponseDto> getAllPosts() {
        return postsRepository.getAllPosts()
                .map(PostsResponseDto::new)
                .collect(Collectors.toList());
    }

    //포스트 id로 포스트 조회
    public PostsResponseDto findById(Long id){
        Posts posts = postsRepository.findById(id).orElseThrow(PostNotFoundException::new);

        return PostsResponseDto.builder()
                .entity(posts)
                .build();
    }

    @Transactional //포스트 수정
    public void editPosts(Long id, PostUpdateDto postUpdateDto) {
        Optional<Posts> optionalPosts = postsRepository.findById(id);
        User user = userRepository.findByEmail(postUpdateDto.getEmail()).orElseThrow(UserNotFoundException::new);

        optionalPosts.ifPresentOrElse(
                posts -> {
                    if(posts.getUser().getId()==user.getId()) //수정 요청한 유저와 작성자가 같은지 확인
                        posts.updatePosts(postUpdateDto);
                    else
                        throw new OperationNotAllowedException();},
                ()->{
                    throw new PostNotFoundException();
                }
        );
    }

    @Transactional //포스트 삭제
    public void deletePosts(Long id, String email){
        Optional<Posts> optionalPosts = postsRepository.findById(id);
        User user = userRepository.findByEmail(email).orElseThrow(UserNotFoundException::new);

        optionalPosts.ifPresentOrElse(
                posts -> {
                    if(posts.getUser().getId()==user.getId()) //삭제 요청한 유저와 작성자가 같은지 확인
                        postsRepository.deletePosts(id);
                    else
                        throw new OperationNotAllowedException(); },
                    ()->{
                        throw new PostNotFoundException();
                    }
        );
    }

    @Transactional
    public List<PostsResponseDto> searchPost(String type, String keyword){

        if(type.equalsIgnoreCase("title")){
            return postsRepository.findByTitleContaining(keyword)
                    .map(PostsResponseDto::new)
                    .collect(Collectors.toList());
        }
        else if(type.equalsIgnoreCase("body")){
            return postsRepository.findByBodyContaining(keyword)
                    .map(PostsResponseDto::new)
                    .collect(Collectors.toList());
        }
        else{
            throw new IllegalArgumentException();
        }
    }
}
