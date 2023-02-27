package com.tomorrow.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.query.Param;

import com.tomorrow.entity.Board;


public interface BoardRepository extends JpaRepository<Board, Long>, QuerydslPredicateExecutor<Board>, BoardRepositoryCustom{
	
	List<Board> findByBoardTitle(String boardTitle);	
	
}
