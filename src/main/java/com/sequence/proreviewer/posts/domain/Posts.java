package com.sequence.proreviewer.posts.domain;

<<<<<<< HEAD
import com.sequence.proreviewer.posts.common.timeEntity.BaseTimeEntity;
import com.sequence.proreviewer.posts.dto.PostUpdateDto;
import com.sequence.proreviewer.user.domain.User;
=======
>>>>>>> d7d9216 (도메인 코드 추가)
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
    private String user_name;

    @Builder
    public Posts(String title, String body, String user_name){
        this.title=title;
        this.body=body;
        this.user_name=user_name;
    }

    public void updatePosts(String title, String body){
        this.title=title;
        this.body=body;
    }
}
