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

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "hire")
@Getter
@Setter
@ToString
public class Hire extends BaseEntity {

	@Id
	@Column(name = "hire_id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;						// 구인공고 식별번호
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "shop_id")
	private Shop shop;						// 매장코드 FK
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "member_id")
	private Member member;					// 회원 아이디 FK
	
	@Column(length = 50, nullable = false)
	private String hireTitle;				// 구인공고제목
	
	@Lob
	@Column(nullable = false)
	private String hireCont;				// 구인공고내용
}
