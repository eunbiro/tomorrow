package com.tomorrow.dto;


import org.modelmapper.ModelMapper;

import com.tomorrow.entity.Review;
import com.tomorrow.entity.ReviewImg;
import com.tomorrow.entity.ShareTipBoard;
import com.tomorrow.entity.ShareTipImg;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ShareTipImgDto {

	private Long id;
	
	private ShareTipBoard shareTipBoard;
	
	private String tipImgNm;
	
	private String tipOriImgNm;
	
	private String tipImgUrl;
	
	private static ModelMapper modelMapper = new ModelMapper();
	
	public static ShareTipImgDto of (ShareTipImg shareTipImg) {
		
		return modelMapper.map(shareTipImg, ShareTipImgDto.class);
	}
}
