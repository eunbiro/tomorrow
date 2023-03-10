package com.tomorrow.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.tomorrow.dto.ReviewDto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "review")
@Getter
@Setter
@ToString
public class Review extends BaseEntity {

	@Id
	@Column(name = "review_id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;						// 매장리뷰 식별번호
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "member_id")
	private Member member;					// 회원 식별번호 FK
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "shop_id")
	private Shop shop;						// 매장코드 FK
	
	@Column(length = 50, nullable = false)
	private String reviewTitle;				// 매장리뷰 제목
	
	@Column(nullable = false, columnDefinition = "LONGTEXT")
	private String reviewCont;				// 매장리뷰 내용
	
	public void updateReview(ReviewDto reviewDto) {
		
		this.reviewTitle = reviewDto.getReviewTitle();
		this.reviewCont = reviewDto.getReviewCont();
	}
}
