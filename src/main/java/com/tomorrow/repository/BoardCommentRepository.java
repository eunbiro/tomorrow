package com.tomorrow.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import com.tomorrow.entity.Board;
import com.tomorrow.entity.BoardComment;

public interface BoardCommentRepository extends JpaRepository<BoardComment, Long>, QuerydslPredicateExecutor<BoardComment>{
	List<BoardComment> findByBoardIdOrderByIdAsc(Long boardId);
	List<BoardComment> findBycreatedBy(String createdBy);
	List<BoardComment> findById(String id);
	
	
}
