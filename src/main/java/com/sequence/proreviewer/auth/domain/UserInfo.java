package com.sequence.proreviewer.auth.domain;

import lombok.Getter;

@Getter
public class UserInfo {

    private String id;
    private String name;
    private String email;

    public String getProviderKey() {
        return this.id;
    }
}
