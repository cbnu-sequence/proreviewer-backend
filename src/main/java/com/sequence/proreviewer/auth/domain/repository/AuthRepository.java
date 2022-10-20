package com.sequence.proreviewer.auth.domain.repository;

import com.sequence.proreviewer.auth.domain.Auth;
import java.util.Optional;

public interface AuthRepository {

    Optional<Auth> findById(Long id);

    Optional<Auth> findByProviderKey(String key);

    Auth saveAuth(Auth auth);
}
