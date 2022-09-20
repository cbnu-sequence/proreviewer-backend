package com.sequence.proreviewer.user.infra.jpa;

import com.sequence.proreviewer.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaUserRepository extends JpaRepository<User, Long> {

}
