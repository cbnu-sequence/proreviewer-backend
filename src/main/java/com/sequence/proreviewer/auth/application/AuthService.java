package com.sequence.proreviewer.auth.application;

import com.google.gson.Gson;
import com.sequence.proreviewer.auth.application.util.JwtProvider;
import com.sequence.proreviewer.auth.domain.Auth;
import com.sequence.proreviewer.auth.domain.Provider;
import com.sequence.proreviewer.auth.domain.UserInfo;
import com.sequence.proreviewer.auth.infra.repository.AuthRepository;
import com.sequence.proreviewer.auth.infra.repository.UserRepository;
import com.sequence.proreviewer.auth.presentation.dto.request.LoginRequestDto;
import com.sequence.proreviewer.auth.presentation.dto.response.AuthTokens;
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

		UserInfo userInfo = this.getGithubUserInfo(env.getProperty("github.user-api.url"),
			accessToken);

		Long loginUserId = login(userInfo);
		return AuthTokens
			.builder()
			.accessToken(jwtProvider.accessToken(String.valueOf(loginUserId)))
			.refreshToken(UUID.randomUUID().toString())
			.build();
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

		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<String> res = restTemplate.exchange(
			urlTemplate,
			HttpMethod.POST,
			entity,
			String.class,
			params
		);

		Gson gson = new Gson();
		System.out.println(gson.fromJson(res.getBody(), HashMap.class));
		return gson
			.fromJson(res.getBody(), HashMap.class)
			.get("access_token")
			.toString();
	}

	private UserInfo getGithubUserInfo(String url, String code) {
		HttpHeaders headers = new HttpHeaders();
		headers.set(HttpHeaders.AUTHORIZATION, "Bearer " + code);
		HttpEntity<HttpHeaders> entity = new HttpEntity<>(headers);

		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<String> res = restTemplate.exchange(
			url,
			HttpMethod.GET,
			entity,
			String.class
		);

		Gson gson = new Gson();
		return gson.fromJson(res.getBody(), UserInfo.class);
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
