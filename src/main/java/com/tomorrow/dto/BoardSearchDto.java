package com.tomorrow.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BoardSearchDto {
	private String searchDateType;
	private String searchBy;
	private String searchQuery= "";
}
