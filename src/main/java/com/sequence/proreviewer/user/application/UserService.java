package com.sequence.proreviewer.user.application;

import com.sequence.proreviewer.user.application.exception.UserNotFoundException;
import com.sequence.proreviewer.user.domain.User;
import com.sequence.proreviewer.user.domain.repository.UserRepository;
import com.sequence.proreviewer.user.presentation.dto.request.UserUpdateRequestDto;
import com.sequence.proreviewer.user.presentation.dto.response.UserResponseDto;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

	@Transactional
	public void updateById(Long id, UserUpdateRequestDto dto) {
		Optional<User> optionalUser = userRepository.findById(id);
		optionalUser.ifPresentOrElse(
			user -> user.update(dto),
			() -> {
				throw new UserNotFoundException();
			}
		);
	}

	public void deleteById(Long id) {
		Optional<User> optionalUser = userRepository.findById(id);
		optionalUser.orElseThrow(UserNotFoundException::new);
		userRepository.deleteById(id);
	}
}
