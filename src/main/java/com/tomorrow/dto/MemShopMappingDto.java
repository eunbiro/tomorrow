package com.tomorrow.dto;

import org.modelmapper.ModelMapper;

import com.tomorrow.entity.MemShopMapping;


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
	
	public static ModelMapper modelMapper = new ModelMapper();
	
	public MemShopMapping createMemShopMapping() {
		return modelMapper.map(this, MemShopMapping.class);
	}
}
