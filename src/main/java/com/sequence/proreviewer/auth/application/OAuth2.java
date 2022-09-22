package com.sequence.proreviewer.auth.application;

import com.google.gson.Gson;
import com.sequence.proreviewer.auth.application.exception.InvalidAccessTokenException;
import com.sequence.proreviewer.auth.application.exception.InvalidAuthorizationCodeException;
import com.sequence.proreviewer.auth.domain.Provider;
import com.sequence.proreviewer.auth.domain.UserInfo;
import java.util.HashMap;
import java.util.Map;
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

@RequiredArgsConstructor
@Component
class OAuth2 {

	private final Environment env;
	private Provider provider;

	UserInfo getUserInfo(Provider provider, String code) {
		settingProvider(provider);
		String accessToken = getAccessToken(code);
		ResponseEntity<String> response = requestUserInfo(accessToken);
		return getUserInfoFromResponseBody(response.getBody());
	}

	private void settingProvider(Provider provider) {
		this.provider = provider;
	}

	private String getAccessToken(String code) {
		ResponseEntity<String> response = requestAccessToken(code);
		return getAccessTokenFromResponseBody(response.getBody());
	}

	private ResponseEntity<String> requestAccessToken(String code) {
		HttpEntity<HttpHeaders> headers = createHttpHeader(
			HttpHeaders.ACCEPT,
			"application/json"
		);
		String urlTemplate = createRequestAccessTokenUrlTemplate();
		Map<String, String> params = createRequestAccessTokenRequestParams(code);

		ResponseEntity<String> response;
		try {
			response = new RestTemplate().exchange(
				urlTemplate,
				HttpMethod.POST,
				headers,
				String.class,
				params
			);
		} catch (RestClientException e) {
			throw new InvalidAuthorizationCodeException();
		}

		if (provider == Provider.GITHUB
			&& new Gson().fromJson(response.getBody(), HashMap.class).get("error") != null) {
			throw new InvalidAuthorizationCodeException();
		}

		return response;
	}

	private ResponseEntity<String> requestUserInfo(String accessToken) {
		String url = getRequestUserInfoUrl();
		HttpEntity<HttpHeaders> headers = createHttpHeader(
			HttpHeaders.AUTHORIZATION,
			"Bearer " + accessToken
		);
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

	private String createRequestAccessTokenUrlTemplate() {
		String requestUrl = getRequestAccessTokenUrl();

		UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder.fromHttpUrl(requestUrl)
			.queryParam("client_id", "{clientId}")
			.queryParam("client_secret", "{clientSecret}")
			.queryParam("code", "{code}");
		if (provider == Provider.GOOGLE) {
			uriComponentsBuilder
				.queryParam("redirect_uri", "{redirectUri}")
				.queryParam("grant_type", "{grantType}");
		}
		uriComponentsBuilder.encode();
		return uriComponentsBuilder.toUriString();
	}

	private Map<String, String> createRequestAccessTokenRequestParams(String code) {
		Map<String, String> params = new HashMap<>();
		params.put("clientId", getClientId());
		params.put("clientSecret", getClientSecret());
		params.put("code", code);
		if (provider == Provider.GOOGLE) {
			params.put("grantType", env.getProperty("google.grant-type"));
			params.put("redirectUri", env.getProperty("google.redirect-uri"));
		}
		return params;
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

	private HttpEntity<HttpHeaders> createHttpHeader(String name, String value) {
		HttpHeaders headers = new HttpHeaders();
		headers.set(name, value);
		return new HttpEntity<>(headers);
	}

	private String getRequestAccessTokenUrl() {
		if (provider == Provider.GITHUB) {
			return env.getProperty("github.access-token.url");
		}
		return env.getProperty("google.access-token.url");
	}

	private String getRequestUserInfoUrl() {
		if (provider == Provider.GITHUB) {
			return env.getProperty("github.user-api.url");
		}
		return env.getProperty("google.user-api.url");
	}

	private String getClientId() {
		if (provider == Provider.GITHUB) {
			return env.getProperty("github.client-id");
		}
		return env.getProperty("google.client-id");
	}

	private String getClientSecret() {
		if (provider == Provider.GITHUB) {
			return env.getProperty("github.client-secret");
		}
		return env.getProperty("google.client-secret");
	}
}