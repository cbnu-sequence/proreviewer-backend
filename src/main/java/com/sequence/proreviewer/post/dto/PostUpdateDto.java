package com.sequence.proreviewer.post.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostUpdateDto {
    String title;
    String body;

    @Builder
    public PostUpdateDto(String title, String body) {
        this.title = title;
        this.body = body;
    }
}
