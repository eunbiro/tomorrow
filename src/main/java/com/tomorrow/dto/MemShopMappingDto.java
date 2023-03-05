package com.tomorrow.dto;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;

import com.tomorrow.entity.MemShopMapping;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemShopMappingDto {

	private MemberFormDto memberFormDto;
	
	private ShopDto shopDto;
	
	private Long mapId;
	
	private int timePay;
	
	private String partTime;
	
	private int workStatus;
	
	private List<MemShopMappingDto> memShopMappingDtoList = new ArrayList<>();
	
	public static ModelMapper modelMapper = new ModelMapper();
	
	public MemShopMapping createMemShopMapping() {
		return modelMapper.map(this, MemShopMapping.class);
	}
	
	public static MemShopMappingDto of(MemShopMapping memShopMapping) {
		return modelMapper.map(memShopMapping,MemShopMappingDto.class);
	}
	
	public void addMemShopMapDto(MemShopMappingDto memShopMappingDto) {
		memShopMappingDtoList.add(memShopMappingDto);
	}


	
	}
