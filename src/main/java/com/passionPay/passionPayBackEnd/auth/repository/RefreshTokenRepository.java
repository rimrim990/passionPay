package com.passionPay.passionPayBackEnd.auth.repository;


import com.passionPay.passionPayBackEnd.auth.domain.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
    Optional<RefreshToken> findByKeyValue(String key);
}
