package com.tomorrow.dto;

import java.time.LocalDateTime;
import java.util.List;

import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NoticeDto {

	private Long noticeId;
	
	private MemberFormDto memberFormDto;
	
	private ShopDto shopDto;
	
	@NotNull(message = "내용은 필수 입력 값입니다.")
	private String noticeCont;
	
	private LocalDateTime regTime;
	
	private LocalDateTime updateTime;
	
	private NoticeLikeDto noticeLikeDto;
	
	private int notiLike;
	
	private List<NoticeLikeDto> noticeLikeDtoList;
}
