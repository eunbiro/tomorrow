package com.tomorrow.dto;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PayListDto {
	private Long id; //급여일지 식별자
	
	private ShopDto shopDto;
	
	private MemberFormDto memberFormDto;	
	
    private MemShopMappingDto memShopMappingDto;
	
	
}
