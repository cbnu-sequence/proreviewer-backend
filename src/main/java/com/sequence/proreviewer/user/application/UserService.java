package com.sequence.proreviewer.user.application;

import com.sequence.proreviewer.user.application.exception.UserNotFoundException;
import com.sequence.proreviewer.user.domain.User;
import com.sequence.proreviewer.user.domain.repository.UserRepository;
import com.sequence.proreviewer.user.presentation.dto.response.UserResponseDto;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

	private final UserRepository userRepository;

	public List<UserResponseDto> findAll() {
		List<User> users = userRepository.findAll();
		return users.stream()
			.map(User::toResponseDto)
			.toList();
	}

	public UserResponseDto findById(Long id) {
		Optional<User> user = userRepository.findById(id);
		return user.orElseThrow(UserNotFoundException::new).toResponseDto();
	}
}
