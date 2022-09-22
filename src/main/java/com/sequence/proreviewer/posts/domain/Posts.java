package com.sequence.proreviewer.posts.domain;

import com.sequence.proreviewer.posts.common.timeEntity.BaseTimeEntity;
import com.sequence.proreviewer.posts.dto.PostUpdateDto;
import com.sequence.proreviewer.user.domain.User;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Getter
@NoArgsConstructor(access= AccessLevel.PROTECTED)
public class Posts extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String title;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String body;

    @NotNull
    private String user_name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    User user;

    @Builder
    public Posts(String title, String body, String user_name, User user){
        this.title=title;
        this.body=body;
        this.user_name=user_name;
        this.user=user;
    }

    public void updatePosts(PostUpdateDto dto){
        this.title= dto.getTitle();
        this.body=dto.getBody();

    }
}
