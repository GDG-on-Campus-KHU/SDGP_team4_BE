package com.team4.domain.post.dao;

import com.team4.domain.post.domain.LikePost;
import com.team4.domain.member.domain.Member;
import com.team4.domain.post.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface LikePostRepository extends JpaRepository<LikePost, Long> {
    List<LikePost> findAllByMember(Member member);

    Optional<LikePost> findByPost(Post post);
}
