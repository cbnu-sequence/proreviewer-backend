package com.sequence.proreviewer.auth.presentation;

import com.sequence.proreviewer.auth.application.AuthService;
import com.sequence.proreviewer.auth.presentation.dto.request.LoginRequestDto;
import com.sequence.proreviewer.auth.presentation.dto.response.AuthTokens;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/auth")
public class AuthController {

	private final AuthService authService;

	@PostMapping("/login/github")
	public AuthTokens github(@RequestBody LoginRequestDto dto) {
		return authService.githubLogin(dto);
	}

}
