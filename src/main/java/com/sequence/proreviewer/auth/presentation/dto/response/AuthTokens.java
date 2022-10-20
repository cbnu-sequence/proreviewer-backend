package com.sequence.proreviewer.auth.presentation.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@ToString
public class AuthTokens {

    private String accessToken;
    private String refreshToken;
}
