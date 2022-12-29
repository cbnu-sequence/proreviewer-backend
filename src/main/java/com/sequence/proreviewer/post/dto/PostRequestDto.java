package com.sequence.proreviewer.post.dto;

import com.sequence.proreviewer.post.domain.Post;
import com.sequence.proreviewer.user.domain.User;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
public class PostRequestDto {
    private String title;
    private String body;
    private User user;

    @Builder
    public PostRequestDto(String title, String body, Long user_id) {
        this.title = title;
        this.body = body;
    }

    public Post toEntity() {
        Post post = Post.builder()
                .title(title)
                .body(body)
                .user(user)
                .build();

        return post;
    }
}
