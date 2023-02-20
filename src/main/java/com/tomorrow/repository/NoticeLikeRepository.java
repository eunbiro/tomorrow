package com.tomorrow.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tomorrow.entity.NoticeLike;

public interface NoticeLikeRepository extends JpaRepository<NoticeLike, Long> {

	Optional<List<NoticeLike>> findByNoticeId(Long noticeId);
}
