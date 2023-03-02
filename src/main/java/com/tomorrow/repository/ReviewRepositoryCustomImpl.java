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
import com.tomorrow.dto.QReviewListDto;
import com.tomorrow.dto.ReviewListDto;
import com.tomorrow.dto.ReviewSearchDto;
import com.tomorrow.entity.QReview;
import com.tomorrow.entity.QReviewImg;

public class ReviewRepositoryCustomImpl implements ReviewRepositoryCustom {

private JPAQueryFactory queryFactory;
	
	public ReviewRepositoryCustomImpl(EntityManager em) {
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
		
		return QReview.review.regTime.after(dateTime); //이후의 시간
	}
	
	private BooleanExpression searchByLike(String searchBy, String searchQuery) {
		if(StringUtils.equals("boardTitle", searchBy)) {
			return QReview.review.reviewTitle.like("%" + searchQuery + "%"); 
		} else if(StringUtils.equals("createdBy", searchBy)) {
			return QReview.review.createdBy.like("%" + searchQuery + "%"); 
		}
		return null;
	}
	
	private BooleanExpression reviewTitleLike(String searchQuery) {
		return StringUtils.isEmpty(searchQuery) ? null : QReview.review.reviewTitle.like("%" + searchQuery + "%");
	}
	

	@Override
	public Page<ReviewListDto> getReviewListPage(ReviewSearchDto reviewSearchDto, Pageable pageable) {
		QReview review =  QReview.review;
		QReviewImg reviewImg = QReviewImg.reviewImg; 
		 
		 List<ReviewListDto> content = queryFactory.select(
					new QReviewListDto(
							review.id,
							review.reviewTitle,
							review.createdBy,
							review.regTime)
					)
					 .from(reviewImg)
					 .join(reviewImg.review, review)
					 .where(reviewTitleLike(reviewSearchDto.getSearchQuery()))
					 .orderBy(review.id.desc())
					 .offset(pageable.getOffset())
					 .limit(pageable.getPageSize())
					 .fetch();
			
			Long total = queryFactory
					.select(Wildcard.count)
					.from(reviewImg)
					.join(reviewImg.review, review)
					.where(reviewTitleLike(reviewSearchDto.getSearchQuery()))
					.fetchOne();

			return new PageImpl<>(content, pageable, total);
	}
}
