package com.tomorrow.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.Length;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.multipart.MultipartFile;

import com.tomorrow.constant.Role;
import com.tomorrow.entity.Member;

@Getter
@Setter
public class MemberFormDto {
	
	@NotEmpty(message = "이메일은 필수 입력 값입니다.")
	private String userId; // 회원 아이디

	@NotBlank(message = "이름은 필수 입력 값입니다.")
	private String userNm; // 회원 이름

	@NotEmpty(message = "비밀번호는 필수 입력 값입니다.")
	@Length(min = 8, max = 16, message = "비밀번호는 8자 이상, 16자 이하로 입력해주세요.")
	private String password; // 회원 비밀번호

	@NotEmpty(message = "전화번호는 필수 입력 값입니다.")
	private String pNum; // 회원 전화번호

	private String oriImgNm; // 원본 이미지명

	private String imgUrl; // 이미지경로

	private String imgNm; // 이미지 이름
	
	private Role role;
	
	private static ModelMapper modelMapper = new ModelMapper();

	public Member createUserProfile() {
		return modelMapper.map(this, Member.class);
	}

	public static MemberFormDto of(Member member) {
		return modelMapper.map(member, MemberFormDto.class);
	}
}
