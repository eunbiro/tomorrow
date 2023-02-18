package com.tomorrow.dto;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WorkLogDto {

	private Long workLogId;
	
	private String partTime;
	
	private String logCont;
	
	private MemberFormDto memberFormDto;
	
	private LocalDateTime regTime;
}
