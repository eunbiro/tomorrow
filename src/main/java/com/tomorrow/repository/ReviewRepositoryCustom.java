package com.tomorrow.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.tomorrow.dto.ReviewListDto;
import com.tomorrow.dto.ReviewSearchDto;

public interface ReviewRepositoryCustom {

	Page<ReviewListDto> getReviewListPage(ReviewSearchDto reviewSearchDto, Pageable pageable);
}
