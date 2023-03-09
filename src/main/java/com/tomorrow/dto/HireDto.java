package com.tomorrow.dto;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.modelmapper.ModelMapper;

import com.tomorrow.entity.Hire;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class HireDto {

	private Long Id;

	private ShopDto shopDto;

	private MemberFormDto memberFormDto;

	@NotNull
	private int hirePay;
	
	@NotNull
	@Size(min = 1, message = "기간/요일은 필수 입력 값입니다.")
	private String hirePeriod;

	@NotNull
	@Size(min = 1, message = "근무타임은 필수 입력 값입니다.")
	private String hireTime;
	
	@NotNull
	@Size(min = 1, message = "전화번호는 필수 입력 값입니다.")
	private String hireNum;
	
	private LocalDateTime regTime;

	private LocalDateTime updateTime;

	private static ModelMapper modelMapper = new ModelMapper();
	
	private List<HireDto> hireDtoList = new ArrayList<>();
	
	public Hire createHire() {
		return modelMapper.map(this, Hire.class);
	}
}