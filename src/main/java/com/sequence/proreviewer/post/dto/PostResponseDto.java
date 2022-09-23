package com.sequence.proreviewer.post.dto;

import com.sequence.proreviewer.post.domain.Post;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor()
public class PostResponseDto {
    private Long id;
    private String title;
    private String body;
    private String user_name;
    private LocalDateTime created_at;
    private LocalDateTime modified_at;

    @Builder
    public PostResponseDto(Post entity){
        this.id= entity.getId();
        this.title=entity.getTitle();
        this.body= entity.getBody();
        this.user_name= entity.getUserName();
        this.created_at=entity.getCreated_at();
        this.modified_at=entity.getModified_at();
    }
}
