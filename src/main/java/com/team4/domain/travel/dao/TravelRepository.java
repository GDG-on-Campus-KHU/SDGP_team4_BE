package com.team4.domain.travel.dao;

import com.team4.domain.member.domain.Member;
import com.team4.domain.post.domain.Post;
import com.team4.domain.travel.domain.Travel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TravelRepository extends JpaRepository<Travel, Long> {
    Page<Travel> findByMember(Pageable pageable, Member member);

    List<Travel> findAllByMember(Member member);

    Optional<Travel> findByPost(Post post);
}
