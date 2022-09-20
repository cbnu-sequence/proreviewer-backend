package com.sequence.proreviewer.user.presentation;

import com.sequence.proreviewer.user.application.UserService;
import com.sequence.proreviewer.user.presentation.dto.request.UserUpdateRequestDto;
import com.sequence.proreviewer.user.presentation.dto.response.UserResponseDto;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RequiredArgsConstructor
@RequestMapping("/users")
@RestController
public class UserController {

	private final UserService userService;

	@GetMapping()
	public List<UserResponseDto> findAll() {
		return userService.findAll();
	}

	@GetMapping("/{id}")
	public UserResponseDto findById(@PathVariable Long id) {
		return userService.findById(id);
	}

	@PutMapping("/{id}")
	public void updateById(@PathVariable Long id, @RequestBody UserUpdateRequestDto dto) {
		userService.updateById(id, dto);
	}
}
