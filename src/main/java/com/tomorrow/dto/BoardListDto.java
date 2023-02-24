package com.tomorrow.dto;

import com.querydsl.core.annotations.QueryProjection;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class BoardListDto {
	private Long id;
	private String boardTitle;	
	private String userNm;
	private Integer viewCount;
	
	@QueryProjection
	public BoardListDto( Long id, String boardTitle, String userNm, Integer viewCount) {
		this.id = id;
		this.boardTitle = boardTitle;
		this.userNm = userNm;
		this.viewCount = viewCount;
	}
}
