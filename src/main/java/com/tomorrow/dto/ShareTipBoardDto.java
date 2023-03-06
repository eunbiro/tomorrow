package com.tomorrow.dto;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotBlank;

import org.modelmapper.ModelMapper;

import com.tomorrow.entity.Member;
import com.tomorrow.entity.Review;
import com.tomorrow.entity.ShareTipBoard;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ShareTipBoardDto {

	private Long id;
	
	@NotBlank(message = "리뷰 제목은 필수 입력 값입니다.")
	private String tipTitle;
	
	@NotBlank(message = "리뷰 내용은 필수 입력 값입니다.")
	private String tipCont;
	
	private String createBy;
	
	private Member member;
	
	private LocalDateTime regTime;
	
	private LocalDateTime updateTime;
	
	private List<ShareTipImgDto> shareTipImgDtoList = new ArrayList<>();
	
	private List<Long> tipImgIds = new ArrayList<>();
	
	
	
	private static ModelMapper modelMapper = new ModelMapper();
	
	public ShareTipBoard createShareTip() {
		
		return modelMapper.map(this, ShareTipBoard.class);
	}
	
	public static ShareTipBoardDto of (ShareTipBoard shareTipBoard) {
		
		return modelMapper.map(shareTipBoard, ShareTipBoardDto.class);
	}
	
}
