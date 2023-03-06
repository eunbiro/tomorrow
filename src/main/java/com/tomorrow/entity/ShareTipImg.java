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
@Table(name = "share_tip_img")
@Getter
@Setter
@ToString
public class ShareTipImg extends BaseEntity {

	@Id
	@Column(name = "tip_img_id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;						// 커뮤니티 이미지 식별번호
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "tip_id")
	private ShareTipBoard shareTipBoard;	// 리뷰게시판 식별번호 FK
	
	private String tipImgNm;					// 이미지명
	
	private String tipOriImgNm;				// 원본이미지명
	
	private String tipImgUrl;	
	
	//원본 이미지 파일명, 업데이트 할 이미지 파일명, 이미지 경로를 파라메터로 받아서 이미지 정보를 업데이트하는 메소드
	public void updateReviewImg(String tipOriImgNm, String tipImgNm, String tipImgUrl){
		this.tipOriImgNm = tipOriImgNm; 
		this.tipImgNm = tipImgNm;
		this.tipImgUrl = tipImgUrl;
	}
}
