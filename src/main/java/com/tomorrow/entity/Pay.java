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
public class Pay {

	@Id
	@Column(name = "pay_id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;						// 시급 식별번호
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "manager_id")
	private Manager manager;				// 관리자 아이디 FK
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "pay_list_id")
	private PayList payList;				// 급여일지 식별번호 FK
	
	@Column(nullable = false)
	private int timePay;					// 시급
}
