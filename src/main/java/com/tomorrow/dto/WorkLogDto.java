package com.tomorrow.dto;

import java.time.LocalDateTime;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WorkLogDto {

	private Long workLogId;
	
	private MemberFormDto memberFormDto;
	
	private ShopDto shopDto;
	
	@NotNull
	@Size(min = 1, message = "내용은 필수 입력 값입니다.")
	private String logCont;
	
	private LocalDateTime regTime;
	
	private LocalDateTime updateTime;
	
	private String partTime;
}
