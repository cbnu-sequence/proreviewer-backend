package com.sequence.proreviewer.auth.infra.repository.jpa.impl;

import com.sequence.proreviewer.auth.infra.repository.UserRepository;
import com.sequence.proreviewer.auth.infra.repository.jpa.JpaUserRepository;
import com.sequence.proreviewer.user.domain.User;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepository {

	private final JpaUserRepository userRepository;


	@Override
	public Optional<User> findByEmail(String email) {
		return userRepository.findByEmail(email);
	}

	@Override
	public User saveUser(User user) {
		return userRepository.save(user);
	}
}
