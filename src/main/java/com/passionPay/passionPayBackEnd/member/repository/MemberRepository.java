package com.passionPay.passionPayBackEnd.member.repository;


import com.passionPay.passionPayBackEnd.member.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByUsername(String username);
    Optional<Member> findById(Long memberId);

    boolean existsByUsername(String username);
}
