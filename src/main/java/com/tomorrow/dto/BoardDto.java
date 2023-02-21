package com.tomorrow.dto;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class BoardDto {
	
	private Long id;
	
	private String boardTitle;
	
	private String boardCont;
		
	private LocalDateTime regTime;
	
	private LocalDateTime updateTime;

}
