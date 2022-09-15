package com.sequence.proreviewer.auth.application;

import com.google.gson.Gson;
import com.sequence.proreviewer.auth.application.util.JwtProvider;
import com.sequence.proreviewer.auth.domain.Auth;
import com.sequence.proreviewer.auth.domain.Provider;
import com.sequence.proreviewer.auth.infra.repository.AuthRepository;
import com.sequence.proreviewer.auth.infra.repository.UserRepository;
import com.sequence.proreviewer.auth.presentation.dto.response.AuthTokens;
import com.sequence.proreviewer.user.domain.User;
import java.util.Optional;
import java.util.UUID;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class AuthService {

	private final AuthRepository authRepository;
	private final UserRepository userRepository;
	private final JwtProvider jwtProvider;

	public AuthTokens github(String accessToken) {
		GithubUserInfo userInfo = this.request("https://api.github.com/user", accessToken);

		Long loginUserId = githubLogin(userInfo);
		return AuthTokens
			.builder()
			.accessToken(jwtProvider.accessToken(String.valueOf(loginUserId)))
			.refreshToken(UUID.randomUUID().toString())
			.build();
	}

	private Long githubLogin(GithubUserInfo userInfo) {
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

	private GithubUserInfo request(String uri, String code) {
		HttpHeaders headers = new HttpHeaders();
		headers.set("Authorization", "Bearer " + code);

		HttpEntity<HttpHeaders> request = new HttpEntity<>(headers);

		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<String> res = restTemplate.exchange(
			uri,
			HttpMethod.GET,
			request,
			String.class
		);

		Gson gson = new Gson();
		return gson.fromJson(res.getBody(), GithubUserInfo.class);
	}

	@Getter
	private class GithubUserInfo {

		private String id;
		private String name;
		private String email;
	}
}
