package com.tomorrow.dto;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NoticeDto {

	private ManagerDto managerDto;
	
	private String noticeCont;
	
	private LocalDateTime regTime;
	
	private int likeNoti;
	
	private ShopDto shopDto;
	
	private Long noticeId;
}
