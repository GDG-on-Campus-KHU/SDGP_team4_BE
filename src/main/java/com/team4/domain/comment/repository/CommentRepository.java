package com.team4.domain.comment.repository;

import com.team4.domain.comment.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

    List<Comment> findByPlace_PlaceId(Long placeId);
    List<Comment> findByPlace_PlaceIdAndIsLocal(Long placeId, boolean isLocal);
}
