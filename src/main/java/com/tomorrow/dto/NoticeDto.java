package com.tomorrow.dto;

import java.time.LocalDateTime;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NoticeDto {

	private Long noticeId;
	
	private MemberFormDto memberFormDto;
	
	private ShopDto shopDto;
	
	@NotBlank
	@Size(min = 1, message = "내용은 필수 입력 값입니다.")
	private String noticeCont;
	
	private LocalDateTime regTime;
	
	private LocalDateTime updateTime;
	
}
