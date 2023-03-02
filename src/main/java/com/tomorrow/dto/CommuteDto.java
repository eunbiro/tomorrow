package com.tomorrow.dto;

import java.time.LocalDateTime;
import org.modelmapper.ModelMapper;

import com.tomorrow.entity.Commute;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class CommuteDto {
	private Long id; //출퇴근 식별자

	private ShopDto shopDto;
	
	private MemberFormDto memberFormDto;	
	
	private LocalDateTime working; //출근
	
	private LocalDateTime leaving; //퇴근
	
	private static ModelMapper modelMapper = new ModelMapper();
	
	//출퇴근기록 등록
	public Commute createCommute() {
 	   return modelMapper.map(this, Commute.class);
    }
	
}
