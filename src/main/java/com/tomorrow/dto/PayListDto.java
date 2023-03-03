package com.tomorrow.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PayListDto {
	private Long id; //급여일지 식별자
	
    private MemShopMappingDto memShopMappingDto;
	
	private int dayPay;
	
	private String regTime;
	
	private int monthPay;
	
	private int workDayCount;
	
	private ShopDto shopDto;
}
