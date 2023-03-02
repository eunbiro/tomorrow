package com.tomorrow.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "review_img")
@Getter
@Setter
@ToString
public class ReviewImg extends BaseEntity {

	@Id
	@Column(name = "rv_img_id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;						// 커뮤니티 이미지 식별번호
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "review_id")
	private Review review;					// 리뷰게시판 식별번호 FK
	
	private String rvImgNm;					// 이미지명
	
	private String rvOriImgNm;				// 원본이미지명
	
	private String rvImgUrl;	
	
	//원본 이미지 파일명, 업데이트 할 이미지 파일명, 이미지 경로를 파라메터로 받아서 이미지 정보를 업데이트하는 메소드
	public void updateReviewImg(String boOriImgNm, String boImgNm, String boImgUrl){
		this.rvOriImgNm = boOriImgNm; 
		this.rvImgNm = boImgNm;
		this.rvImgUrl = boImgUrl;
	}
}
