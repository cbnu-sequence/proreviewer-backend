package com.sequence.proreviewer.auth.application.oauth2;

import com.google.gson.Gson;
import com.sequence.proreviewer.auth.domain.UserInfo;
import java.util.HashMap;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

public abstract class OAuth2 {

    public UserInfo getUserInfo(String code) {
        String accessToken = getAccessToken(code);
        ResponseEntity<String> response = requestUserInfo(
            accessToken,
            createHttpHeaders(HttpHeaders.AUTHORIZATION, getBearerAccessToken(accessToken)),
            getUserInfoRequestUrl()
        );
        return getUserInfoFromResponseBody(response.getBody());
    }

    private String getAccessToken(String code) {
        ResponseEntity<String> response = requestAccessToken(
            code,
            createHttpHeaders(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE),
            createRequestAccessTokenUrlTemplate(getAccessTokenRequestUrl()),
            createRequestAccessTokenParams(code)
        );
        return getAccessTokenFromResponseBody(response.getBody());
    }

    private String getAccessTokenFromResponseBody(String responseBody) {
        return new Gson()
            .fromJson(responseBody, HashMap.class)
            .get("access_token")
            .toString();
    }

    private UserInfo getUserInfoFromResponseBody(String responseBody) {
        return new Gson().fromJson(responseBody, UserInfo.class);
    }

    private String getBearerAccessToken(String accessToken) {
        return "Bearer " + accessToken;
    }

    private HttpEntity<HttpHeaders> createHttpHeaders(String name, String value) {
        HttpHeaders headers = new HttpHeaders();
        headers.set(name, value);
        return new HttpEntity<>(headers);
    }

    abstract ResponseEntity<String> requestAccessToken(
        String code,
        HttpEntity<HttpHeaders> headers,
        String urlTemplate,
        HashMap<String, String> params
    );

    abstract ResponseEntity<String> requestUserInfo(
        String accessToken,
        HttpEntity<HttpHeaders> headers,
        String url
    );

    abstract String createRequestAccessTokenUrlTemplate(String requestUrl);

    abstract HashMap<String, String> createRequestAccessTokenParams(String code);

    abstract String getAccessTokenRequestUrl();

    abstract String getUserInfoRequestUrl();

    abstract String getClientId();

    abstract String getClientSecret();
}
