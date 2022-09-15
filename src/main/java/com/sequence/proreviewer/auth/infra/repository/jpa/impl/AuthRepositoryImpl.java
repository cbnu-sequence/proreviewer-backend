package com.sequence.proreviewer.auth.infra.repository.jpa.impl;

import com.sequence.proreviewer.auth.domain.Auth;
import com.sequence.proreviewer.auth.infra.repository.AuthRepository;
import com.sequence.proreviewer.auth.infra.repository.jpa.JpaAuthRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class AuthRepositoryImpl implements AuthRepository {

	private final JpaAuthRepository authRepository;

	public Optional<Auth> findById(Long id) {
		return authRepository.findById(id);
	}

	@Override
	public Optional<Auth> findByProviderKey(String key) {
		return authRepository.findByProviderKey(key);
	}

	@Override
	public Auth saveAuth(Auth auth) {
		return authRepository.save(auth);
	}
}
