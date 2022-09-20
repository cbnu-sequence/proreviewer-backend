package com.sequence.proreviewer.user.infra.jpa;

import com.sequence.proreviewer.user.domain.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaUserRepository extends JpaRepository<User, Long> {

	Optional<User> findByEmail(String email);
}
