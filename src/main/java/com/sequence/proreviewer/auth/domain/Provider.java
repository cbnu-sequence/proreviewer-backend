package com.sequence.proreviewer.auth.domain;

import com.sequence.proreviewer.auth.application.oauth2.GithubOAuth2;
import com.sequence.proreviewer.auth.application.oauth2.GoogleOAuth2;
import com.sequence.proreviewer.auth.application.oauth2.OAuth2;

public enum Provider {
    GOOGLE(GoogleOAuth2.class),
    GITHUB(GithubOAuth2.class);

    private final Class<? extends OAuth2> oAuth2ConcreteClass;

    Provider(Class<? extends OAuth2> oAuth2ConcreteClass) {
        this.oAuth2ConcreteClass = oAuth2ConcreteClass;
    }
}
