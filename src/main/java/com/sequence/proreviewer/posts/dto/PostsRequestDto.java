package com.sequence.proreviewer.posts.dto;

import com.sequence.proreviewer.posts.domain.Posts;
import com.sequence.proreviewer.user.domain.User;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
public class PostsRequestDto {
    private String title;
    private String body;
    private String user_name;
    private User user;

    @Builder
    public PostsRequestDto(String title, String body, String user_name){
        this.title=title;
        this.body=body;
        this.user_name=user_name;
    }

    public Posts toEntity(){
        Posts posts = Posts.builder()
                .title(title)
                .body(body)
                .user_name(user_name)
                .user(user)
                .build();

        return posts;
    }
}
