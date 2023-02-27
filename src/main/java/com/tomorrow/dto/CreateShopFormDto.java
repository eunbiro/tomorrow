package com.tomorrow.dto;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.modelmapper.ModelMapper;

import com.tomorrow.entity.MemShopMapping;
import com.tomorrow.entity.Shop;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateShopFormDto { //매장 만들 때 
	private Long id;  //매장 코드
	
	@NotBlank(message = "매장명은 필수 입력 값입니다.")
	private String shopNm; //매장 이름
	
	private String shopTime; //매장 시간
	
	@NotNull(message = "사업자 번호는 필수 입력 값입니다.")
	private Long businessId; //사업자 번호
	
	@NotBlank(message = "매장 위치는는 필수 입력 값입니다.")
	private String shopPlace;
	
	@NotBlank(message = "매장 업종은 필수 입력 값입니다.")
	private String shopType;
	
	private List<ShopImgDto> createShopImgDtoList = new ArrayList<>();
	
	private List<Long> createShopImgIds = new ArrayList<>();
	
	public static ModelMapper modelMapper = new ModelMapper();
	
	public Shop createShop() {
		return modelMapper.map(this, Shop.class);
		
	}
	
	public MemShopMapping createMemShopMapping() {
		return modelMapper.map(this, MemShopMapping.class);
	}
	
	public static CreateShopFormDto of(Shop shop) {
		return modelMapper.map(shop, CreateShopFormDto.class);
	}
}
