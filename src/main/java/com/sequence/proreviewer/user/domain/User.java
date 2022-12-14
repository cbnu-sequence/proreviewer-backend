package com.sequence.proreviewer.user.domain;

import com.sequence.proreviewer.auth.domain.Auth;
import com.sequence.proreviewer.user.presentation.dto.request.UserUpdateRequestDto;
import com.sequence.proreviewer.user.presentation.dto.response.UserResponseDto;
import java.util.List;
import java.util.UUID;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String email;

    @NotNull
    private String name;

    private String description;

    @OneToMany(mappedBy = "user", cascade = {CascadeType.REMOVE})
    private List<Auth> auths;

    @Builder
    public User(String email) {
        this.email = email;
        this.name = UUID.randomUUID().toString();
    }

    public Long getId() {
        return this.id;
    }

    public UserResponseDto toResponseDto() {
        return UserResponseDto.builder()
            .id(this.id)
            .email(this.email)
            .name(this.name)
            .description(this.description)
            .build();
    }

    public void update(UserUpdateRequestDto dto) {
        this.name = dto.getName();
        this.description = dto.getDescription();
    }

    public void withdrawal() {
        this.auths = null;
    }
}
