package com.sequence.proreviewer.auth.presentation;

import com.sequence.proreviewer.auth.application.AuthService;
import com.sequence.proreviewer.auth.domain.Provider;
import com.sequence.proreviewer.auth.presentation.dto.request.LoginRequestDto;
import com.sequence.proreviewer.auth.presentation.dto.response.AuthTokens;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/auth")
public class AuthController {

	private final AuthService authService;

	@PostMapping("/login/{provider}")
	public AuthTokens login(@RequestBody LoginRequestDto dto, @PathVariable String provider) {
		if (Provider.valueOf(provider.toUpperCase()) == Provider.GITHUB) {
			return authService.login(Provider.GITHUB, dto);
		}
		return authService.login(Provider.GOOGLE, dto);
	}
}
