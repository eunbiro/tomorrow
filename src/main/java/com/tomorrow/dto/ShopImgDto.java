package com.tomorrow.dto;

import org.modelmapper.ModelMapper;

import com.tomorrow.entity.ShopImg;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ShopImgDto {
	private Long id;
	
	private String shImgNm;
	
	private String shOriImgNm;
	
	private String shImgUrl;
	
	private static ModelMapper modelMapper = new ModelMapper();
	
	public static ShopImgDto of(ShopImg shopImg) {
		return modelMapper.map(shopImg, ShopImgDto.class);
	}
}
