package com.tomorrow.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.multipart.MultipartFile;

import com.tomorrow.constant.Role;
import com.tomorrow.entity.Member;

@Getter
@Setter
public class MemberFormDto {
	
	private Long id;	//회원 식별자
	
	@NotEmpty(message = "이메일은 필수 입력 값입니다.")
	private String userId; // 회원 아이디

	@NotBlank(message = "이름은 필수 입력 값입니다.")
	private String userNm; // 회원 이름

	@NotEmpty(message = "비밀번호는 필수 입력 값입니다.")
	@Length(min = 8, max = 16, message = "비밀번호는 8자 이상, 16자 이하로 입력해주세요.")
	private String password; // 회원 비밀번호
	
	@NotEmpty(message = "전화번호는 필수 입력 값입니다.")
	@Length(min = 13, max = 13, message = "-포함 13자리를 입력해주세요.")
	@Pattern(regexp = "^(010|011|016|017|018|019)[-]?\\d{3,4}[-]?\\d{4}$", message = "전화번호를 형식에 맞게 입력해 주세요")
	private String pNum; // 회원 전화번호

	private String oriImgNm; // 원본 이미지명

	private String imgUrl; // 이미지경로

	private String imgNm; // 이미지 이름
	
	@NotEmpty(message = "질문은 필수 입력 값입니다.")
	private String hintQ;	//비밀번호 수정 시 사용
	
	@NotEmpty(message = "질문 대답은 필수 입력 값입니다.")
	private String hintA;	//비밀번호 수정 시 사용
	
	private Role role;
	
	private LocalDateTime regTime;
	
	private String provider;    // oauth2를 이용할 경우 어떤 플랫폼을 이용하는지
	
    private String providerId;  // oauth2를 이용할 경우 아이디값
	
	private int unregister;
	
	private static ModelMapper modelMapper = new ModelMapper();
	
	public Member createUserProfile() {
		return modelMapper.map(this, Member.class);
	}

	public static MemberFormDto of(Member member) {
		return modelMapper.map(member, MemberFormDto.class);
	}
	
	//회원수정
	public Member updateMemberInfo(Member member, PasswordEncoder passwordEncoder) {
		member.setPassword(passwordEncoder.encode(this.password));
		return member;
	}
}
