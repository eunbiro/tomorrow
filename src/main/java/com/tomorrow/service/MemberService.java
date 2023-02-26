package com.tomorrow.service;

import java.util.Map;
import java.util.regex.Pattern;

import org.apache.groovy.parser.antlr4.util.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.tomorrow.dto.BoardFormDto;
import com.tomorrow.dto.MemberFormDto;
import com.tomorrow.entity.Member;
import com.tomorrow.repository.MemberRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberService implements UserDetailsService {

	@Value("${userProfileImgLocation}")
	private String userProfileImgLocation;
	private final FileService fileService;
	private final MemberRepository memberRepository;

	@Override
	public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {

		Member member = memberRepository.findByUserId(userName);

		if (member == null) {
			throw new UsernameNotFoundException(userName);
		}

		return User.builder().username(member.getUserId()).password(member.getPassword())
				.roles(member.getRole().toString()).build();
	}

	public Member saveMember(Member member) {
		validateDuplicateMember(member);
		return memberRepository.save(member);
	}

	public void validateDuplicateMember(Member member) {
		Member findMember = memberRepository.findByUserId(member.getUserId());
		if (findMember != null) {
			throw new IllegalStateException("이미 가입된 회원입니다.");
		}
	}

	public Member saveProfileImg(Member member, MultipartFile profileImgFile) throws Exception {
		String oriImgName = profileImgFile.getOriginalFilename(); // 파일 이름
		String imgName = ""; // 기본 이미지 만들기
		String imgUrl = ""; // 기본 이미지 url 만들기

		// 파일 업로드
		if (!StringUtils.isEmpty(oriImgName)) {
			imgName = fileService.uploadFile(userProfileImgLocation, oriImgName, profileImgFile.getBytes());
			imgUrl = "/images/profile/" + imgName;
		} else {
			oriImgName = "";
		}
		// 상품 이미지 정보 저장
		member.updateUserImg(oriImgName, imgName, imgUrl);

		return member;
	}

	// 로그인한 사용자 정보 가져오기
	@Transactional(readOnly = true) // 트랜잭션 읽기 전용(변경감지 수행하지 않음) -> 성능향상
	public MemberFormDto getIdImgUrl(String memberId) {
		// 1. item_img테이블의 이미지를 가져온다.
		Member member = memberRepository.findByUserId(memberId);

		// 엔티티 객체 -> dto객체로 변환
		MemberFormDto memberFormDto = MemberFormDto.of(member);

		return memberFormDto;
	}
	
	public Member findByNmPhone(String name, String pNum) {
		 return memberRepository.findByMemberId(name, pNum);
	}
	/*
	@Transactional(readOnly = true) // 트랜잭션 읽기 전용(변경감지 수행하지 않음) -> 성능향상
	public String findId(String name, String id_phone) {
		String result = "";
		try {
			result = memberRepository.findByMemberId(name, id_phone);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	//전화번호 형식에 맞게 쓰기
	public boolean isPhoneNum(String id_phone) {
		//전화번호 정규식
		String check = "^01(?:0|1|[6-9])-(?:\\d{3}|\\d{4})-\\d{4}$";
		return Pattern.matches(check, id_phone);
	}
	*/
}
