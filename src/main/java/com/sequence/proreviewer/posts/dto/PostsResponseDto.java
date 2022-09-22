package com.sequence.proreviewer.posts.dto;

import com.sequence.proreviewer.posts.domain.Posts;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor()
public class PostsResponseDto {
    private Long id;
    private String title;
    private String body;
    private String user_name;
    private LocalDateTime created_at;
    private LocalDateTime modified_at;

    @Builder
    public PostsResponseDto(Posts entity){
        this.id= entity.getId();
        this.title=entity.getTitle();
        this.body= entity.getBody();
        this.user_name= entity.getUserName();
        this.created_at=entity.getCreated_at();
        this.modified_at=entity.getModified_at();
    }
}
