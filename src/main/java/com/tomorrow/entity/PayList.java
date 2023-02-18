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
@Table(name = "pay_list")
@Getter
@Setter
@ToString
public class PayList extends BaseEntity  {

	/* 급여일지 작성 시 급여일지 테이블을 이용하는 이유는 시급과 근무시간은 유동적이기 때문에
	 * 추후 이력을 조회할 때 현재 데이터를 통해 과거 내용까지 계산해서 조회할 수 있게되니까
	 * 현재 계산 내용을 여기 테이블에 저장해서 출력하는걸로 계획했습니다.
	 * 원래 매장코드는 연결이 안되어있었는데 추가했습니다.
	 */
	
	@Id
	@Column(name = "pay_list_id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;						// 급여일지 식별번호
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "member_id")
	private Member member;					// 회원 식별번호 FK
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "shop_id")
	private Shop shop;						// 매장코드 FK
}
