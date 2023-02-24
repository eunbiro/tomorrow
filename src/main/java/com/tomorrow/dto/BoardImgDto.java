package com.tomorrow.dto;

import org.modelmapper.ModelMapper;
import com.tomorrow.entity.Board;
import com.tomorrow.entity.BoardImg;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BoardImgDto {
	private Long id;
	
	private Board board;					// 커뮤니티 식별번호 FK
	private String boImgNm;					// 이미지명
	private String boOriImgNm;				// 원본이미지명
	private String boImgUrl;	
	
	private static ModelMapper modelMapper = new ModelMapper();
	
	public static BoardImgDto of(BoardImg boardImg) {
		return modelMapper.map(boardImg, BoardImgDto.class);
	}
}
