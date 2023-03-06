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
import com.tomorrow.dto.QShareTipListDto;
import com.tomorrow.dto.ShareTipListDto;
import com.tomorrow.dto.ShareTipSearchDto;
import com.tomorrow.entity.QShareTipBoard;
import com.tomorrow.entity.QShareTipImg;

public class ShareTipRepositoryCustomImpl implements ShareTipRepositoryCustom {

private JPAQueryFactory queryFactory;
	
	public ShareTipRepositoryCustomImpl(EntityManager em) {
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
		
		return QShareTipBoard.shareTipBoard.regTime.after(dateTime); //이후의 시간
	}
	
	private BooleanExpression searchByLike(String searchBy, String searchQuery) {
		if(StringUtils.equals("rvTitle", searchBy)) {
			return QShareTipBoard.shareTipBoard.tipTitle.like("%" + searchQuery + "%"); 
		} else if(StringUtils.equals("createdBy", searchBy)) {
			return QShareTipBoard.shareTipBoard.createdBy.like("%" + searchQuery + "%"); 
		}
		return null;
	}
	
	private BooleanExpression tipTitleLike(String searchQuery) {
		return StringUtils.isEmpty(searchQuery) ? null : QShareTipBoard.shareTipBoard.tipTitle.like("%" + searchQuery + "%");
	}
	

	@Override
	public Page<ShareTipListDto> getShareTipListPage(ShareTipSearchDto shareTipSearchDto, Pageable pageable) {
		QShareTipBoard shareTipBoard = QShareTipBoard.shareTipBoard;
		QShareTipImg shareTipImg = QShareTipImg.shareTipImg;
		
		List<ShareTipListDto> content = queryFactory.select(
					new QShareTipListDto(
								shareTipBoard.id,
								shareTipBoard.tipTitle,
								shareTipBoard.createdBy,
								shareTipBoard.regTime)
				)
				.from(shareTipImg)
				.join(shareTipImg.shareTipBoard, shareTipBoard)
				.where(tipTitleLike(shareTipSearchDto.getSearchQuery()))
				.orderBy(shareTipBoard.id.desc())
				.offset(pageable.getOffset())
				.limit(pageable.getPageSize())
				.fetch();
		
		Long total = queryFactory
				.select(Wildcard.count)
				.from(shareTipImg)
				.join(shareTipImg.shareTipBoard, shareTipBoard)
				.where(tipTitleLike(shareTipSearchDto.getSearchQuery()))
				.fetchOne();

			return new PageImpl<>(content, pageable, total);
	}
}
