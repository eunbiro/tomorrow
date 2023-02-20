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
@Table(name = "user_profile")
@Getter
@Setter
@ToString
public class UserProfile {

	@Id
	@Column(name = "user_pro_id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;	//회원 프로필 식별자
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "member_id")
	private Member member;	//회원식별
	
	private String proOriImgNm;	//원본 이미지명
	
	private String proImgUrl; //이미지경로
	
	public void updateUserImg(String proOriImgNm, String proImgUrl) {
		this.proOriImgNm = proOriImgNm;
		this.proImgUrl = proImgUrl;
	}
}
