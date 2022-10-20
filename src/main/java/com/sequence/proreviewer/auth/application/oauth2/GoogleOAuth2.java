package com.sequence.proreviewer.auth.application.oauth2;

import com.sequence.proreviewer.auth.application.exception.InvalidAccessTokenException;
import com.sequence.proreviewer.auth.application.exception.InvalidAuthorizationCodeException;
import java.util.HashMap;
import lombok.RequiredArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Component
@RequiredArgsConstructor
public class GoogleOAuth2 extends OAuth2 {

    private final Environment env;

    @Override
    ResponseEntity<String> requestAccessToken(
        String code,
        HttpEntity<HttpHeaders> headers,
        String urlTemplate,
        HashMap<String, String> params
    ) {
        try {
            return new RestTemplate().exchange(
                urlTemplate,
                HttpMethod.POST,
                headers,
                String.class,
                params
            );
        } catch (RestClientException e) {
            throw new InvalidAuthorizationCodeException();
        }
    }

    @Override
    ResponseEntity<String> requestUserInfo(
        String accessToken,
        HttpEntity<HttpHeaders> headers,
        String url
    ) {
        try {
            return new RestTemplate().exchange(
                url,
                HttpMethod.GET,
                headers,
                String.class
            );
        } catch (RestClientException e) {
            throw new InvalidAccessTokenException();
        }
    }

    @Override
    String createRequestAccessTokenUrlTemplate(String requestUrl) {
        return UriComponentsBuilder
            .fromHttpUrl(requestUrl)
            .queryParam("client_id", "{clientId}")
            .queryParam("client_secret", "{clientSecret}")
            .queryParam("code", "{code}")
            .queryParam("redirect_uri", "{redirectUri}")
            .queryParam("grant_type", "{grantType}")
            .encode()
            .toUriString();
    }

    @Override
    HashMap<String, String> createRequestAccessTokenParams(String code) {
        HashMap<String, String> params = new HashMap<>();
        params.put("clientId", getClientId());
        params.put("clientSecret", getClientSecret());
        params.put("code", code);
        params.put("grantType", env.getProperty("google.grant-type"));
        params.put("redirectUri", env.getProperty("google.redirect-uri"));
        return params;
    }

    @Override
    String getAccessTokenRequestUrl() {
        return env.getProperty("google.access-token.url");
    }

    @Override
    String getUserInfoRequestUrl() {
        return env.getProperty("google.user-api.url");
    }

    @Override
    String getClientId() {
        return env.getProperty("google.client-id");
    }

    @Override
    String getClientSecret() {
        return env.getProperty("google.client-secret");
    }
}
