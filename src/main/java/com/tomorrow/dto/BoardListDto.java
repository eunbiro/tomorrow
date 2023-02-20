package com.tomorrow.dto;

import com.querydsl.core.annotations.QueryProjection;
import com.tomorrow.entity.Member;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class BoardListDto {
	private Long id;
	private String boardTitle;	
	private String createdBy;
	private Integer viewCount;
	
	@QueryProjection
	public BoardListDto( Long id, String boardTitle, String createdBy, Integer viewCount) {
		this.id = id;
		this.boardTitle = boardTitle;
		this.createdBy = createdBy;
		this.viewCount = viewCount;
	}
}
