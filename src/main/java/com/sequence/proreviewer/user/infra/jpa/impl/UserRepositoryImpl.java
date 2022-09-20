package com.sequence.proreviewer.user.infra.jpa.impl;

import com.sequence.proreviewer.user.domain.repository.UserRepository;
import com.sequence.proreviewer.user.infra.jpa.JpaUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class UserRepositoryImpl implements UserRepository {

	private final JpaUserRepository userRepository;
}
