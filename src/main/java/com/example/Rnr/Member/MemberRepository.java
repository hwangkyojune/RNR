package com.example.Rnr.Member;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member,Long> {
    boolean existsByLoginId(String loginId);
    boolean existsByName(String name);
    Optional<Member> findByLoginId(String loginId);
}
