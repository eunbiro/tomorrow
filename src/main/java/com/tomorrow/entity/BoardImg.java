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
@Table(name = "board_img")
@Getter
@Setter
@ToString
public class BoardImg {

	@Id
	@Column(name = "bo_img_id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;						// 커뮤니티 이미지 식별번호
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "board_id")
	private Board board;					// 커뮤니티 식별번호 FK
	
	private String boImgNm;					// 이미지명
	
	private String boOriImgNm;				// 원본이미지명
	
	private String boImgUrl;				// 이미지경로
}
