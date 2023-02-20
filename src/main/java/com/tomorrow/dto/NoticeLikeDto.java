package com.tomorrow.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NoticeLikeDto {

	private Long notiLikeId;
	
	private MemberFormDto memberFormDto;
	
	private NoticeDto noticeDto;
	
	private int likeCount;
}
