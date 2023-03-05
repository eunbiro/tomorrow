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

	@Id
	@Column(name = "pay_list_id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;						// 급여일지 식별번호
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "map_id")
	private MemShopMapping memShopMapping;					// 관리자 아이디 FK
	
	private int dayPay;
	
	public static PayList createPayList(int dayPay, MemShopMapping memShopMapping) {
		
		PayList payList = new PayList();
		payList.setDayPay(dayPay);
		payList.setMemShopMapping(memShopMapping);
		
		return payList;
	}
}
