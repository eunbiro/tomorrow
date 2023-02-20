package com.tomorrow.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.springframework.security.crypto.password.PasswordEncoder;

import com.tomorrow.constant.Role;
import com.tomorrow.dto.MemberFormDto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "member")
@Getter
@Setter
@ToString
public class Member extends BaseEntity {

	@Id
	@Column(name = "member_id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;					// 회원 식별자
	
	@Column(length = 25, nullable = false)
	private String userId;				// 유저 회원가입/로그인 시 필요한 id
	
	@Column(length = 20, nullable = false)
	private String userNm;				// 유저 회원가입 시 필요한 이름
	
	@Column(nullable = false)
	private String password;			// 유저 회원가입/로그인 시 필요한 비밀번호
	
	@Column(length = 13, nullable = false)
	private String pNum;				// 유저 회원가입 시 필요한 전화번호
	
	@Enumerated(EnumType.STRING)
	private Role role;					// 유저 권한등급 > 기본 회원가입 시 무조건 USER 등급
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "shop_id")
	private Shop shop;					// 매장코드 FK
	
	public static Member createMember(MemberFormDto memberFormDto, PasswordEncoder passwordEncoder) {
		Member member = new Member();
		member.setUserNm(memberFormDto.getUserNm());
		member.setUserId(memberFormDto.getUserId());
		member.setPNum(memberFormDto.getPNum());
		
		String password = passwordEncoder.encode(memberFormDto.getPassword()); //비밀번호 암호화
		member.setPassword(password);
		
		member.setRole(Role.USER);
		
		return member;
	}
}
