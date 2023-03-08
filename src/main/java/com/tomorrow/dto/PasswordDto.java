package com.tomorrow.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PasswordDto {
	private String nowPassword; //현재
	
	private String checkPassword; //내가 비교할 비밀번호
}
