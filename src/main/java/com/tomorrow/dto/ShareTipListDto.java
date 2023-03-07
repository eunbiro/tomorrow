package com.tomorrow.dto;

import java.time.LocalDateTime;

import com.querydsl.core.annotations.QueryProjection;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ShareTipListDto {

	private Long id;
	
	private String tipTitle;
	
	private String userNm;
	
	private LocalDateTime regTime;
	
	@QueryProjection
	public ShareTipListDto (Long id, String tipTitle, String userNm, LocalDateTime regTime) {
		
		this.id = id;
		this.tipTitle = tipTitle;
		this.userNm = userNm;
		this.regTime = regTime;
	}
}
