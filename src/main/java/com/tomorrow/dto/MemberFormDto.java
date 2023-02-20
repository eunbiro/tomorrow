package com.tomorrow.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.Length;

import com.tomorrow.entity.UserProfile;

@Getter
@Setter
public class MemberFormDto {
	
	@NotEmpty (message = "이메일은 필수 입력 값입니다.")
	private String userId; //회원 아이디
	
	@NotBlank (message = "이름은 필수 입력 값입니다.")
	private String userNm;	//회원 이름
	
	@NotEmpty (message = "비밀번호는 필수 입력 값입니다.")
	@Length(min = 8, max = 16, message = "비밀번호는 8자 이상, 16자 이하로 입력해주세요.")
	private String password;	//회원 비밀번호
	
	@NotEmpty (message = "전화번호는 필수 입력 값입니다.")
	private String pNum;	//회원 전화번호
	
	private String userProfile;	//회원 이미지
	
	private List<UserProfile> userProfileImg;	//유저의 프로필 이미지를 저장함
}
