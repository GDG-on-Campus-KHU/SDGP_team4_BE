package com.team4.domain.member.repository;

import com.team4.domain.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, Integer> {

    Optional<Member> findById(Integer memberId);

    Optional<Member> findByNickname(String nickname);
}
