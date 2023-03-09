package com.tomorrow.dto;

import java.time.LocalDateTime;

import com.querydsl.core.annotations.QueryProjection;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class HireListDto {
	
	private Long id;
	
	private int hirePay;
	
	private String hirePeriod;
	
	private String hireTime;
	
	private String hireNum;
	
	private ShopDto shopDto;
	
	private MemberFormDto memberFormDto;
	

}
