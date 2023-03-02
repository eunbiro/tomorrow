package com.tomorrow.dto;

import java.time.LocalDateTime;

import com.querydsl.core.annotations.QueryProjection;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReviewListDto {

	private Long id;
	
	private String ReviewTitle;
	
	private String userNm;
	
	private LocalDateTime regTime;
	
	@QueryProjection
	public ReviewListDto (Long id, String reviewTitle, String userNm, LocalDateTime regTime) {
		
		this.id = id;
		this.ReviewTitle = reviewTitle;
		this.userNm = userNm;
		this.regTime = regTime;
	}
}
