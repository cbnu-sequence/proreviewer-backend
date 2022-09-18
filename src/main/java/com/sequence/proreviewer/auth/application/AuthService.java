package com.sequence.proreviewer.auth.application;

import com.google.gson.Gson;
import com.sequence.proreviewer.auth.application.exception.InvalidAuthorizationCodeException;
import com.sequence.proreviewer.auth.application.exception.InvalidGithubAccessTokenException;
import com.sequence.proreviewer.auth.application.util.JwtProvider;
import com.sequence.proreviewer.auth.domain.Auth;
import com.sequence.proreviewer.auth.domain.Provider;
import com.sequence.proreviewer.auth.domain.UserInfo;
import com.sequence.proreviewer.auth.infra.repository.AuthRepository;
import com.sequence.proreviewer.auth.infra.repository.UserRepository;
import com.sequence.proreviewer.auth.presentation.dto.request.LoginRequestDto;
import com.sequence.proreviewer.auth.presentation.dto.response.AuthTokens;
import com.sequence.proreviewer.common.error.ErrorCode;
import com.sequence.proreviewer.user.domain.User;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Service
@RequiredArgsConstructor
public class AuthService {

	private final AuthRepository authRepository;
	private final UserRepository userRepository;
	private final JwtProvider jwtProvider;
	private final Environment env;

	public AuthTokens githubLogin(LoginRequestDto dto) {
		String accessToken = this.getGithubAccessToken(
			env.getProperty("github.access-token.url"),
			dto.getCode()
		);

		UserInfo userInfo = this.getGithubUserInfo(
			env.getProperty("github.user-api.url"),
			accessToken
		);

		Long loginUserId = login(userInfo);
		return AuthTokens.builder()
			.accessToken(jwtProvider.accessToken(String.valueOf(loginUserId)))
			.refreshToken(UUID.randomUUID().toString())
			.build();
	}

	public AuthTokens googleLogin(LoginRequestDto dto) {
		String accessToken = this.getGoogleAccessToken(
			env.getProperty("google.access-token.url"),
			dto.getCode()
		);

		UserInfo userInfo = this.getGoogleUserInfo(env.getProperty("google.user-api.url"), accessToken);

		Long loginUserId = login(userInfo);
		return AuthTokens.builder()
			.accessToken(jwtProvider.accessToken(String.valueOf(loginUserId)))
			.refreshToken(UUID.randomUUID().toString())
			.build();
	}

	private String getGoogleAccessToken(String url, String code) {
		String clientId = env.getProperty("google.client-id");
		String clientSecret = env.getProperty("google.client-secret");

		HttpHeaders headers = new HttpHeaders();
		headers.set(HttpHeaders.ACCEPT, "application/json");
		HttpEntity<HttpHeaders> entity = new HttpEntity<>(headers);

		String urlTemplate = UriComponentsBuilder.fromHttpUrl(url)
			.queryParam("client_id", "{clientId}")
			.queryParam("client_secret", "{clientSecret}")
			.queryParam("code", "{code}")
			.queryParam("redirect_uri", "{redirectUri}")
			.queryParam("grant_type", "{grantType}")
			.encode()
			.toUriString();

		Map<String, String> params = new HashMap<>();
		params.put("clientId", clientId);
		params.put("clientSecret", clientSecret);
		params.put("code", code);
		params.put("grantType", "authorization_code");
		params.put("redirectUri", env.getProperty("google.redirect-uri"));

		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<String> res = restTemplate.exchange(
			urlTemplate,
			HttpMethod.POST,
			entity,
			String.class,
			params
		);

		return new Gson()
			.fromJson(res.getBody(), HashMap.class)
			.get("access_token")
			.toString();
	}

	private String getGithubAccessToken(String url, String code) {
		String clientId = env.getProperty("github.client-id");
		String clientSecret = env.getProperty("github.client-secret");

		HttpHeaders headers = new HttpHeaders();
		headers.set(HttpHeaders.ACCEPT, "application/json");
		HttpEntity<HttpHeaders> entity = new HttpEntity<>(headers);

		String urlTemplate = UriComponentsBuilder.fromHttpUrl(url)
			.queryParam("client_id", "{clientId}")
			.queryParam("client_secret", "{clientSecret}")
			.queryParam("code", "{code}")
			.encode()
			.toUriString();

		Map<String, String> params = new HashMap<>();
		params.put("clientId", clientId);
		params.put("clientSecret", clientSecret);
		params.put("code", code);

		ResponseEntity<String> res = new RestTemplate().exchange(
			urlTemplate,
			HttpMethod.POST,
			entity,
			String.class,
			params
		);
		HashMap<String, String> resMap = new Gson().fromJson(res.getBody(), HashMap.class);

		String err = resMap.get("error");
		if (err != null) {
			throw new InvalidAuthorizationCodeException();
		}

		return resMap.get("access_token");
	}

	private UserInfo getGithubUserInfo(String url, String accessToken) {
		HttpHeaders headers = new HttpHeaders();
		headers.set(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken);
		HttpEntity<HttpHeaders> entity = new HttpEntity<>(headers);

		ResponseEntity<String> res;
		try {
			res = new RestTemplate().exchange(
				url,
				HttpMethod.GET,
				entity,
				String.class
			);
		} catch (RestClientException e) {
			throw new InvalidGithubAccessTokenException();
		}

		return new Gson().fromJson(res.getBody(), UserInfo.class);
	}

	private UserInfo getGoogleUserInfo(String url, String accessToken) {
		HttpHeaders headers = new HttpHeaders();
		headers.set(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken);
		HttpEntity<HttpHeaders> entity = new HttpEntity<>(headers);

		ResponseEntity<String> res = new RestTemplate().exchange(
			url,
			HttpMethod.GET,
			entity,
			String.class
		);

		return new Gson().fromJson(res.getBody(), UserInfo.class);
	}

	private Long login(UserInfo userInfo) {
		Optional<User> exUser = userRepository.findByEmail(userInfo.getEmail());
		if (exUser.isEmpty()) {
			User user = User
				.builder()
				.email(userInfo.getEmail())
				.build();
			exUser = Optional.ofNullable(userRepository.saveUser(user));
		}

		Optional<Auth> exAuth = this.authRepository.findByProviderKey(userInfo.getId());
		if (exAuth.isEmpty()) {
			Auth auth = Auth
				.builder()
				.provider(Provider.GITHUB)
				.providerKey(userInfo.getId())
				.userId(exUser.get())
				.build();
			authRepository.saveAuth(auth);
		}

		return exUser.get().getId();
	}
}
