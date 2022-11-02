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
    private String user_name;
    private Long user_id;
    private User user;

    @Builder
    public PostRequestDto(String title, String body, String user_name, Long user_id){
        this.title=title;
        this.body=body;
        this.user_name=user_name;
        this.user_id = user_id;
    }

    public Post toEntity(){
        Post post = Post.builder()
                .title(title)
                .body(body)
                .user_name(user_name)
                .user(user)
                .build();

        return post;
    }
}
