package com.tomorrow.dto;


import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;

import com.tomorrow.entity.Shop;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ShopDto {
	private Long shopId; // 매장코드 (자동생성)
	
	private String shopNm; // 매장이름
	
	private String shopTime; // 영업시간 
	
	private Long businessId; // 사업자번호 
	
	private String shopPlace; // 매장위치
	
	private String shopType; // 매장업종
	
	private List<ShopImgDto> shopImgDto = new ArrayList<>(); // 상품 이미지 정보 저장하는 리스트 
	
}
