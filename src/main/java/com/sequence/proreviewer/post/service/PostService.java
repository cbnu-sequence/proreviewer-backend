package com.sequence.proreviewer.post.service;

import com.sequence.proreviewer.post.common.exception.OperationNotAllowedException;
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

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class PostService {
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    @Transactional  //post 작성
    public Long write(PostRequestDto postRequestDto, String email){

        User user = userRepository.findByEmail(email).orElseThrow(UserNotFoundException::new); //작성자 정보

        postRequestDto.setUser(user);

        Post post = postRequestDto.toEntity();

        postRepository.savePost(post);

        return (post.getId());
    }

    //모든 포스트 조회
    public List<PostResponseDto> getAllPosts() {
        return postRepository.getAllPosts()
                .map(PostResponseDto::new)
                .collect(Collectors.toList());
    }

    //포스트 id로 포스트 조회
    public PostResponseDto findById(Long id){
        Post post = postRepository.findById(id).orElseThrow(PostNotFoundException::new);

        return PostResponseDto.builder()
                .entity(post)
                .build();
    }

    @Transactional //포스트 수정
    public void editPost(Long id, PostUpdateDto postUpdateDto) {
        Optional<Post> optionalPost = postRepository.findById(id);
        User user = userRepository.findByEmail(postUpdateDto.getEmail()).orElseThrow(UserNotFoundException::new);

        optionalPost.ifPresentOrElse(
                post -> {
                    if(post.getUser().getId()==user.getId()) //수정 요청한 유저와 작성자가 같은지 확인
                        post.updatePost(postUpdateDto);
                    else
                        throw new OperationNotAllowedException();},
                ()->{
                    throw new PostNotFoundException();
                }
        );
    }

    @Transactional //포스트 삭제
    public void deletePost(Long id, String email){
        Optional<Post> optionalPost = postRepository.findById(id);
        User user = userRepository.findByEmail(email).orElseThrow(UserNotFoundException::new);

        optionalPost.ifPresentOrElse(
                post -> {
                    if(post.getUser().getId()==user.getId()) //삭제 요청한 유저와 작성자가 같은지 확인
                        postRepository.deletePost(id);
                    else
                        throw new OperationNotAllowedException(); },
                    ()->{
                        throw new PostNotFoundException();
                    }
        );
    }

    @Transactional
    public List<PostResponseDto> searchPost(int type, String keyword){
                                    //type : 1 제목, 2 본문, 3 작성자

        if(type==1){
            return postRepository.findByTitleContaining(keyword)
                    .map(PostResponseDto::new)
                    .collect(Collectors.toList());
        }
        else if(type==2){
            return postRepository.findByBodyContaining(keyword)
                    .map(PostResponseDto::new)
                    .collect(Collectors.toList());
        }
        else if(type==3){
            return postRepository.findByUsernameContaining(keyword)
                    .map(PostResponseDto::new)
                    .collect(Collectors.toList());
        }
        else{
            throw new IllegalArgumentException();
        }
    }
}
