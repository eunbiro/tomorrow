package com.tomorrow.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import com.tomorrow.entity.ShareTipBoard;

public interface ShareTipBoardRepository extends JpaRepository<ShareTipBoard, Long>, QuerydslPredicateExecutor<ShareTipBoard>, ShareTipRepositoryCustom {

	List<ShareTipBoard> findByTipTitle(String tipTitle);

	Optional<ShareTipBoard> findByIdOrderByIdDesc(Long tipId);
}
