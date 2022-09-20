package com.sequence.proreviewer.user.domain.repository;

import com.sequence.proreviewer.user.domain.User;
import java.util.List;
import java.util.Optional;

public interface UserRepository {

	List<User> findAll();

	Optional<User> findById(Long id);

	Optional<User> findByEmail(String email);

	User save(User user);
}
