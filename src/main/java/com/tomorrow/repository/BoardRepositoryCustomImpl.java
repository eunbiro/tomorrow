package com.tomorrow.repository;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.thymeleaf.util.StringUtils;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Wildcard;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.tomorrow.dto.BoardListDto;
import com.tomorrow.dto.BoardSearchDto;
import com.tomorrow.dto.QBoardListDto;
import com.tomorrow.entity.QBoard;
import com.tomorrow.entity.QBoardImg;

public class BoardRepositoryCustomImpl implements BoardRepositoryCustom{
	
	private JPAQueryFactory queryFactory;
	
	public BoardRepositoryCustomImpl(EntityManager em) {
		this.queryFactory = new JPAQueryFactory(em);
	}

	private BooleanExpression regDtsAfter(String searchDateType) {
		LocalDateTime dateTime = LocalDateTime.now(); 
		
		//현재 날짜로 부터 이전 날짜를 구해준다.
		if(StringUtils.equals("all", searchDateType) || searchDateType == null)  return null;
		else if(StringUtils.equals("1d", searchDateType)) dateTime = dateTime.minusDays(1); 
		else if(StringUtils.equals("1w", searchDateType)) dateTime = dateTime.minusWeeks(1);
		else if(StringUtils.equals("1m", searchDateType)) dateTime = dateTime.minusMonths(1);
		else if(StringUtils.equals("6m", searchDateType)) dateTime = dateTime.minusMonths(6);
		
		return QBoard.board.regTime.after(dateTime); //이후의 시간
	}

	private BooleanExpression searchByLike(String searchBy, String searchQuery) {
		if(StringUtils.equals("boardTitle", searchBy)) {
			return QBoard.board.boardTitle.like("%" + searchQuery + "%"); 
		} else if(StringUtils.equals("createdBy", searchBy)) {
			return QBoard.board.createdBy.like("%" + searchQuery + "%"); 
		}
		return null;
	}
	
	private BooleanExpression boardTitleLike(String searchQuery) {
		return StringUtils.isEmpty(searchQuery) ? null : QBoard.board.boardTitle.like("%" + searchQuery + "%");
	}
	
	@Override
	public Page<BoardListDto> getBoardListPage(BoardSearchDto boardSearchDto, Pageable pageable) {
		 QBoard board =  QBoard.board;
		 QBoardImg boardImg = QBoardImg.boardImg; 
		 
			List<BoardListDto> content = queryFactory.select(
					new QBoardListDto(
							board.id,
							board.boardTitle,
							board.createdBy,
							board.viewCount
							)
					).from(boardImg)
					 .join(boardImg.board, board)
					 .where(boardTitleLike(boardSearchDto.getSearchQuery()))
					 .orderBy(board.id.desc())
					 .offset(pageable.getOffset())
					 .limit(pageable.getPageSize())
					 .fetch();
			
			Long total = queryFactory.select(Wildcard.count)
					.from(boardImg)
					.join(boardImg.board, board)
					 .where(boardTitleLike(boardSearchDto.getSearchQuery()))
					.fetchOne();

			return new PageImpl<>(content, pageable, total);
	}

}
