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
@Table(name = "pay")
@Getter
@Setter
@ToString
public class shopEmpl {
	
	/* 시급 테이블을 알바관리 테이블로 변경했음
	 * 
	 * 이유 : 시급이랑 근무타입을 저장할 수 있는 테이블이 필요했음
	 * 		 그걸 회원테이블에 넣자니 매장이 여러개일 때 관리가 안되기 때문에 관리 테이블로 따로 변경했습니다.
	 */

	@Id
	@Column(name = "pay_id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;						// 시급 식별번호
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "manager_id")
	private Member member;					// 관리자 아이디 FK
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "shop_id")
	private Shop shop;						// 매장코드 FK
	
	@Column(nullable = false)
	private int timePay;					// 시급
	
	@Column(length = 30, nullable = false)
	private String partTime;				// 파트타임(오전,오후,야간)
}
