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
@Table(name = "hire_list")
@Getter
@Setter
@ToString
public class HireList {
	
	@Id
	@Column(name = "hire_list_id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;						// 구인공고 식별번호
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "shop_id")
	private Shop shop;						// 매장코드 FK
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "member_id")
	private Member member;					// 회원 아이디 FK
	

}
