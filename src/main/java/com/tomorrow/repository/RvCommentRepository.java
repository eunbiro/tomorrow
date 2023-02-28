package com.tomorrow.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import com.tomorrow.entity.RvComment;

public interface RvCommentRepository extends JpaRepository<RvComment, Long>, QuerydslPredicateExecutor<RvComment>, ReviewRepositoryCustom  {

	List<RvComment> findByReviewIdOrderByIdAsc(Long reviewId);
	List<RvComment> findByCreatedBy(String createdBy);
	List<RvComment> findById(String Id);
}
