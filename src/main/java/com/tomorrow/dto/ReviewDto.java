package com.tomorrow.dto;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotBlank;

import org.modelmapper.ModelMapper;

import com.tomorrow.entity.Member;
import com.tomorrow.entity.Review;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReviewDto {

	private Long id;
	
	@NotBlank(message = "리뷰 제목은 필수 입력 값입니다.")
	private String reviewTitle;
	
	@NotBlank(message = "리뷰 내용은 필수 입력 값입니다.")
	private String reviewCont;
	
	private String createBy;
	
	private Member member;
	
	private LocalDateTime regTime;
	
	private LocalDateTime updateTime;
	
	private List<ReviewImgDto> reviewImgDtoList = new ArrayList<>();
	
	private List<Long> ReviewImgIds = new ArrayList<>();
	
	
	
	private static ModelMapper modelMapper = new ModelMapper();
	
	public Review createReview() {
		
		return modelMapper.map(this, Review.class);
	}
	
	public static ReviewDto of (Review review) {
		
		return modelMapper.map(review, ReviewDto.class);
	}
	
}
