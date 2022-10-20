package com.sequence.proreviewer.auth.application.oauth2;

import com.google.gson.Gson;
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
public class GithubOAuth2 extends OAuth2 {

    private final Environment env;

    @Override
    ResponseEntity<String> requestAccessToken(
        String code,
        HttpEntity<HttpHeaders> headers,
        String urlTemplate,
        HashMap<String, String> params
    ) {
        ResponseEntity<String> response = new RestTemplate().exchange(
            urlTemplate,
            HttpMethod.POST,
            headers,
            String.class,
            params
        );

        if (getErrorFromResponseBody(response.getBody()) != null) {
            throw new InvalidAuthorizationCodeException();
        }

        return response;
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
            .encode()
            .toUriString();
    }

    @Override
    HashMap<String, String> createRequestAccessTokenParams(String code) {
        HashMap<String, String> params = new HashMap<>();
        params.put("clientId", getClientId());
        params.put("clientSecret", getClientSecret());
        params.put("code", code);
        return params;
    }

    @Override
    String getAccessTokenRequestUrl() {
        return env.getProperty("github.access-token.url");
    }

    @Override
    String getUserInfoRequestUrl() {
        return env.getProperty("github.user-api.url");
    }

    @Override
    String getClientId() {
        return env.getProperty("github.client-id");
    }

    @Override
    String getClientSecret() {
        return env.getProperty("github.client-secret");
    }

    private String getErrorFromResponseBody(String responseBody) {
        return (String) new Gson()
            .fromJson(responseBody, HashMap.class)
            .get("error");
    }
}
