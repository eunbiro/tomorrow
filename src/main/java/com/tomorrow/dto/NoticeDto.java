package com.tomorrow.dto;

import java.time.LocalDateTime;
import java.util.List;

import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NoticeDto {

	private MemberFormDto memberFormDto;
	
	@NotNull(message = "공지내용은 필수 입력 값입니다.")
	private String noticeCont;
	
	private LocalDateTime regTime;
	
	@NotNull(message = "매장은 필수 입력 값입니다.")
	private ShopDto shopDto;
	
	private Long noticeId;
	
	private NoticeLikeDto noticeLikeDto;
	
	private int notiLike;
	
	private List<NoticeLikeDto> noticeLikeDtoList;
}
