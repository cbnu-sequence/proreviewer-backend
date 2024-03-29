package com.sequence.proreviewer.post.service;

import com.sequence.proreviewer.post.common.exception.PostNotFoundException;
import com.sequence.proreviewer.post.common.searchType.SearchType;
import com.sequence.proreviewer.post.domain.Post;
import com.sequence.proreviewer.post.dto.PostRequestDto;
import com.sequence.proreviewer.post.dto.PostResponseDto;
import com.sequence.proreviewer.post.dto.PostUpdateDto;
import com.sequence.proreviewer.post.infra.repository.PostRepository;
import com.sequence.proreviewer.user.application.exception.UserNotFoundException;
import com.sequence.proreviewer.user.domain.User;
import com.sequence.proreviewer.user.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RequiredArgsConstructor
@Service
public class PostService {
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    @Transactional
    public Long write(PostRequestDto postRequestDto, Long userId) {

        User user = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);

        postRequestDto.setUser(user);

        Post post = postRequestDto.toEntity();

        postRepository.save(post);

        return (post.getId());
    }

    @Transactional(readOnly = true)
    public List<PostResponseDto> findAll() {

        List<PostResponseDto> postResponseDtoList;

        try (Stream<Post> postStream = postRepository.findAll()) {
            postResponseDtoList = postStream
                    .map(PostResponseDto::fromEntity)
                    .collect(Collectors.toList());
        }

        return postResponseDtoList;
    }

    @Transactional(readOnly = true)
    public PostResponseDto findById(Long id) {
        Optional<Post> optionalPost = postRepository.findById(id);
        Post post = optionalPost.orElseThrow(PostNotFoundException::new);

        return PostResponseDto.fromEntity(post);
    }

    @Transactional
    public void edit(Long id, PostUpdateDto postUpdateDto) {
        Optional<Post> optionalPost = postRepository.findById(id);

        optionalPost.ifPresentOrElse(
                post -> {
                    post.updatePost(postUpdateDto);
                },
                () -> {
                    throw new PostNotFoundException();
                }
        );
    }

    @Transactional
    public void delete(Long id) {
        Optional<Post> optionalPost = postRepository.findById(id);

        optionalPost.ifPresentOrElse(
                post -> {
                    postRepository.deleteById(id);
                },
                () -> {
                    throw new PostNotFoundException();
                }
        );
    }

    @Transactional(readOnly = true)
    public List<PostResponseDto> search(SearchType type, String keyword) {

        Stream<Post> postStream;

        switch (type) {
            case TITLE:
                postStream = postRepository.findByTitleContaining(keyword);
                break;
            case BODY:
                postStream = postRepository.findByBodyContaining(keyword);
                break;
            case AUTHOR:
                postStream = postRepository.findByUserId(keyword);
                break;
            default:
                throw new IllegalArgumentException();
        }

        try (postStream) {
            return postStream
                    .map(PostResponseDto::fromEntity)
                    .collect(Collectors.toList());
        }
    }
}
