package com.tomorrow.dto;

import com.tomorrow.entity.Manager;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ShopDto {

	
	private Long ShopId;
	
	private String shopNm;
	
	private String shopTime;
	
	private int businessId;
	
	private String shopPlace;
	
	private String shopType;
	
	private Manager manager;
}
