package com.redditClone.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.redditClone.model.VerificationToken;
import java.util.Optional;

public interface VerificationTokenRepository extends JpaRepository<VerificationToken, Long>{
    Optional<VerificationToken> findByToken(String token);

}
