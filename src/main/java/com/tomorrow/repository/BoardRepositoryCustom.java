package com.tomorrow.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.tomorrow.dto.BoardSearchDto;
import com.tomorrow.entity.Board;

public interface BoardRepositoryCustom {
	
	Page<Board> getAdminBoardPage(BoardSearchDto boardSearchDto, Pageable pageable);
	
	

}
