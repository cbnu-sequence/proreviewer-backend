package com.sequence.proreviewer.auth.infra.jpa;

import com.sequence.proreviewer.auth.domain.Auth;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaAuthRepository extends JpaRepository<Auth, Long> {

	Optional<Auth> findByProviderKey(String key);
}
