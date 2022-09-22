package com.sequence.proreviewer.posts.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Getter
@NoArgsConstructor(access= AccessLevel.PROTECTED)
public class Posts extends BaseTimeEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String title;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String body;

    @NotNull
    private String userName;

    @Builder
    public Posts(String title, String body, String user_name){
        this.title=title;
        this.body=body;
        this.userName=user_name;
        this.user=user;
    }

    public void updatePosts(String title, String body){
        this.title=title;
        this.body=body;
    }
}
