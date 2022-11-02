package com.sequence.proreviewer.post.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostUpdateDto {
    String title;
    String body;

    @Builder
    public PostUpdateDto(String title, String body, String email){
        this.title=title;
        this.body=body;
    }
}
