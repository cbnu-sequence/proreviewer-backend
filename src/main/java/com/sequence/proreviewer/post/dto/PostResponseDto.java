package com.sequence.proreviewer.post.dto;

import com.sequence.proreviewer.post.domain.Post;
import com.sequence.proreviewer.user.domain.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor()
public class PostResponseDto {
    private Long id;
    private Long user_id;
    private String title;
    private String body;
    private LocalDateTime created_at;
    private LocalDateTime modified_at;

    @Builder
    public PostResponseDto(Post entity) {
        User user = entity.getUser();

        this.id = entity.getId();
        this.user_id = user.getId();
        this.title = entity.getTitle();
        this.body = entity.getBody();
        this.created_at = entity.getCreated_at();
        this.modified_at = entity.getModified_at();
    }
}
