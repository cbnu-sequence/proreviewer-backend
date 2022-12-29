package com.sequence.proreviewer.post.domain;

import com.sequence.proreviewer.post.common.timeEntity.BaseTimeEntity;
import com.sequence.proreviewer.post.dto.PostUpdateDto;
import com.sequence.proreviewer.user.domain.User;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Post extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String title;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String body;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Builder
    public Post(String title, String body, User user) {
        this.title = title;
        this.body = body;
        this.user = user;
    }

    public void updatePost(PostUpdateDto dto) {
        this.title = dto.getTitle();
        this.body = dto.getBody();
    }
}
