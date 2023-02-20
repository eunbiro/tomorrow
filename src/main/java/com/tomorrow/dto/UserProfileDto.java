package com.tomorrow.dto;

import org.modelmapper.ModelMapper;

import com.tomorrow.entity.UserProfile;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserProfileDto {

	private Long id; // 회원식별

	private String proOriImgNm; // 원본 이미지명

	private String proImgUrl; // 이미지경로
	
	private static ModelMapper modelMapper = new ModelMapper();

	public static UserProfileDto of(UserProfile profileImg) {
		return modelMapper.map(profileImg, UserProfileDto.class);
	}
}
