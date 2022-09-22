package com.sequence.proreviewer.posts.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostUpdateDto {
    String title;
    String body;
    String email;

    @Builder
    public PostUpdateDto(String title, String body, String email){
        this.title=title;
        this.body=body;
        this.email=email;
    }
}
