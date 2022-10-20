package com.sequence.proreviewer.auth.domain;

import com.sequence.proreviewer.user.domain.User;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import lombok.Builder;
import lombok.RequiredArgsConstructor;

@Entity
@RequiredArgsConstructor
public class Auth {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @NotNull
    private String providerKey;

    @Enumerated(EnumType.STRING)
    @NotNull
    private Provider provider;

    @Builder
    public Auth(User user, String providerKey, Provider provider) {
        this.user = user;
        this.providerKey = providerKey;
        this.provider = provider;
    }
}
