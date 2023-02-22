package com.tomorrow.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import com.tomorrow.entity.BoardComment;

public interface BoardCommentRepository extends JpaRepository<BoardComment, Long>, QuerydslPredicateExecutor<BoardComment>{
	List<BoardComment>  findBycreatedBy();
	List<BoardComment>  findById();
}
