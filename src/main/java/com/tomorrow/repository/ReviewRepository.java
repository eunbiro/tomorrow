package com.tomorrow.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import com.tomorrow.entity.Review;

public interface ReviewRepository extends JpaRepository<Review, Long>, QuerydslPredicateExecutor<Review>, ReviewRepositoryCustom {

	List<Review> findByReviewTitle(String reviewTitle);
}
