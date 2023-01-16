package com.sequence.proreviewer.post.dto;

import com.sequence.proreviewer.post.domain.Post;
import com.sequence.proreviewer.user.domain.User;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor()
public class PostResponseDto {
    private Long id;
    private Long userId;
    private String title;
    private String body;
    private LocalDateTime created_at;
    private LocalDateTime modified_at;

    private PostResponseDto(Long id, Long userId, String title, String body, LocalDateTime created_at, LocalDateTime modified_at) {
        this.id = id;
        this.userId = userId;
        this.title = title;
        this.body = body;
        this.created_at = created_at;
        this.modified_at = modified_at;
    }

    public static PostResponseDto fromEntity(Post entity) {
        User user = entity.getUser();

        return new PostResponseDto(
                entity.getId(),
                user.getId(),
                entity.getTitle(),
                entity.getBody(),
                entity.getCreated_at(),
                entity.getModified_at()
        );
    }
}
