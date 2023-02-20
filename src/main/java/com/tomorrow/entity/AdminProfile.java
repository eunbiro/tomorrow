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
@Table(name = "admin_profile")
@Getter
@Setter
@ToString
public class AdminProfile {

	@Id
	@Column(name = "admin_pro_id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;						// 공지댓글 식별번호
	
	private String adminProNm;				// 프로필명
	
	private String proOriImgNm;				// 원본이미지명
	
	private String proImgUrl;				// 이미지경로.
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "manager_id")
	private Manager manager;				// 관리자번호 FK
}
