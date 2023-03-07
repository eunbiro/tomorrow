package com.tomorrow.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.tomorrow.dto.MemShopMappingDto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "mem_shop_mapping")
@Getter
@Setter
@ToString
public class MemShopMapping extends BaseEntity{
	
	/* 시급 테이블을 매장회원 다대다 매핑 테이블로 변경했음
	 * 
	 * 이유 : 시급이랑 근무타입을 저장할 수 있는 테이블이 필요했음
	 * 		 그걸 회원테이블에 넣자니 매장이 여러개일 때 관리가 안되기 때문에 관리 테이블로 따로 변경했습니다.
	 */

	@Id
	@Column(name = "map_id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;						// 시급 식별번호
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "member_id")
	private Member member;					// 관리자 아이디 FK
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "shop_id")
	private Shop shop;						// 매장코드 FK
	
	
	private int timePay;					// 시급
	
	@Column(length = 30)
	private String partTime;				// 파트타임(오전,오후,야간)
	
	private int workStatus;					// 알바 상태 0: 관리자, 1: 승인대기, 2: 승인완료, 3: 퇴사, 4: 휴직
	
	@OneToMany(mappedBy = "memShopMapping")
	private List<PayList> PayLists = new ArrayList<>();
	
	// 직원등록
	public static MemShopMapping createMemMapping(Shop shop, Member member) {
		
		MemShopMapping mapping = new MemShopMapping();
		mapping.setShop(shop);
		mapping.setMember(member);
		mapping.setWorkStatus(1);
		mapping.setPartTime("");
		mapping.setTimePay(0);
		
		return mapping;
	}
	
	public static MemShopMapping createAdminMapping(Shop shop, Member member) {
		
		MemShopMapping mapping = new MemShopMapping();
		mapping.setShop(shop);
		mapping.setMember(member);
		mapping.setWorkStatus(0);
		mapping.setPartTime("관리자");
		mapping.setTimePay(0);
		
		return mapping;
	}
	
	// 상태 업데이트 
	public void updateStatus(MemShopMappingDto memShopMappingDto, Member member, Shop shop) {
		this.shop = shop;
		this.member = member;
		this.workStatus = 2;
	}

	// 직원 정보 수정
	public void updateEmplInfo(MemShopMappingDto memShopMappingDto, Member member, Shop shop) {
		this.shop = shop;
		this.member = member;
		this.partTime = memShopMappingDto.getPartTime();
		this.timePay = memShopMappingDto.getTimePay();
	}
	
}
