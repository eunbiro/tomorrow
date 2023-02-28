package com.tomorrow.dto;

import java.time.LocalDateTime;

import org.modelmapper.ModelMapper;

import com.tomorrow.entity.Commute;
import com.tomorrow.entity.Member;
import com.tomorrow.entity.Shop;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ManagerCommuteDto {
	private Long id;
	
	private Member member;
	
	private Shop shop;
	
	private LocalDateTime working; //출근
		
	private LocalDateTime leaving; //퇴근
	
	private static ModelMapper modelMapper = new ModelMapper();

	public Commute createCommute() {
 	   return modelMapper.map(this, Commute.class);
    }
}
