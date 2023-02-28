package com.tomorrow.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tomorrow.entity.ReviewImg;

public interface ReviewImgRepository extends JpaRepository<ReviewImg, Long> {

	List<ReviewImg> findByReviewIdOrderByIdAsc(Long reviewId);
}
