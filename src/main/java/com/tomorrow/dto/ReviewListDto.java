package com.tomorrow.dto;

import com.querydsl.core.annotations.QueryProjection;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReviewListDto {

	private Long id;
	
	private String ReviewTitle;
	
	private String userNm;
	
	@QueryProjection
	public ReviewListDto (Long id, String reviewTitle, String userNm) {
		
		this.id = id;
		this.ReviewTitle = reviewTitle;
		this.userNm = userNm;
	}
}
