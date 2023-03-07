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

import com.tomorrow.dto.HireDto;

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
	
	@Column(nullable = false)
	private int hirePay;       		    	// 구인공고 시급
	
	@Column(nullable = false)
	private String hirePeriod;				// 구인공고 기간/요일
	
	@Column(nullable = false)
	private String hireTime;                // 구인공고 근무타임
	
	public static Hire createHire(HireDto hireDto, Member member, Shop shop) {
		Hire hire = new Hire();
		
		hire.setShop(shop);
		hire.setMember(member);
		hire.setHirePay(hireDto.getHirePay());
		hire.setHirePeriod(hireDto.getHirePeriod());
		hire.setHireTime(hireDto.getHireTime());
		
		return hire;
		
	}
}
