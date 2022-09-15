package com.sequence.proreviewer.auth.infra.repository;

import com.sequence.proreviewer.user.domain.User;
import java.util.Optional;

public interface UserRepository {

	Optional<User> findByEmail(String email);

	User saveUser(User user);
}
