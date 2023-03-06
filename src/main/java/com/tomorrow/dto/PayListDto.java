package com.tomorrow.dto;

import org.modelmapper.ModelMapper;

import com.tomorrow.entity.PayList;
import com.tomorrow.dto.PayListDto;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PayListDto {
	private Long id; //급여일지 식별자
	
    private MemShopMappingDto memShopMappingDto;
	
	private int dayPay;
	
	private int workDays;
	
	private String regTime;
	
	private int monthPay;
	
	private ShopDto shopDto;
	
	public static ModelMapper modelMapper = new ModelMapper();
	
	public PayList createPayList() {
		return modelMapper.map(this, PayList.class);
	}

	public static PayListDto of(PayList payList) {
		return modelMapper.map(payList, PayListDto.class);
	}
}