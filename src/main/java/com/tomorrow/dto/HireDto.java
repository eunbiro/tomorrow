package com.tomorrow.dto;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;

import com.tomorrow.entity.Hire;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class HireDto {
	
	private Long hireId;	// 구인공고 식별번호
	
	private ShopDto shopDto;	// 매장코드 FK
	
	private MemberFormDto memberFormDto;		// 회원 아이디 FK
	
	private String hireTitle;	// 구인공고제목
	
	private String hireCont; 	// 구인공고내용
	
	private List<HireDto> hireDtoList = new ArrayList<>();	// 구인공고 목록
	
	public static ModelMapper modelMapper = new ModelMapper();	// 변환용
	
	// Dto -> Entity
	public Hire createHire() {
		return modelMapper.map(this, Hire.class);
	}
	
	// Entity -> Dto
	public static HireDto of(Hire hire) {
		return modelMapper.map(hire,HireDto.class);
	}
	
	// Dto List
	public void addHireDto(HireDto hireDto) {
		hireDtoList.add(hireDto);
	}

}
