package com.tomorrow.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemShopMappingDto {

	private MemberFormDto memberFormDto;
	
	private ShopDto shopDto;
	
	private int timePay;
	
	private String partTime;
	
	private int workStatus;
}
