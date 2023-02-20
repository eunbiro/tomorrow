package com.tomorrow.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import com.tomorrow.entity.Board;


public interface BoardRepository extends JpaRepository<Board, Long>, QuerydslPredicateExecutor<Board>{
	
	
	List<Board> findByBoardTitle(String boardTitle);
	
}
