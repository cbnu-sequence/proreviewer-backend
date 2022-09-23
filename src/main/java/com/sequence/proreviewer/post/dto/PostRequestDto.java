package com.sequence.proreviewer.post.dto;

import com.sequence.proreviewer.post.domain.Post;
import com.sequence.proreviewer.user.domain.User;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class PostRequestDto {
    private String title;
    private String body;
    private String user_name;

    @Builder
    public PostRequestDto(String title, String body, String user_name){
        this.title=title;
        this.body=body;
        this.user_name=user_name;
    }

    public Post toEntity(){
        Post post = Post.builder()
                .title(title)
                .body(body)
                .user_name(user_name)
                .build();

        return post;
    }
}
