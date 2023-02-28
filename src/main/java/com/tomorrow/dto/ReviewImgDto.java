package com.tomorrow.dto;


import org.modelmapper.ModelMapper;

import com.tomorrow.entity.Review;
import com.tomorrow.entity.ReviewImg;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReviewImgDto {

	private Long id;
	
	private Review review;
	
	private String rvImgNm;
	
	private String rvOriImgNm;
	
	private String rvImgUrl;
	
	private static ModelMapper modelMapper = new ModelMapper();
	
	public static ReviewImgDto of (ReviewImg reviewImg) {
		
		return modelMapper.map(reviewImg, ReviewImgDto.class);
	}
}
