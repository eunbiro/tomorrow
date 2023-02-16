package com.tomorrow.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.springframework.data.annotation.CreatedDate;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "commute")
@Getter
@Setter
@ToString
public class Commute {

	@Id
	@Column(name = "commute_id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;						// 출퇴근 식별번호
	
	@CreatedDate							// 엔티티가 생성 후 저장될 때 시간을 자동으로 저장
	@Column(updatable = false)				// 이 컬럼은 등록만 되고 수정은 불가
	private LocalDateTime working;			// 출근날짜시간
	
	@CreatedDate
	@Column(updatable = false)
	private LocalDateTime leaving;			// 퇴근날짜시간
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "shop_id")
	private Shop shop;						// 매장코드 FK
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "member_id")
	private Member member;					// 회원 식별번호 FK
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "pay_list_id")
	private PayList payList;				// 급여일지 식별번호 FK
}
