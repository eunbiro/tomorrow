package com.tomorrow.service;

import org.apache.groovy.parser.antlr4.util.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

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
	
	//아이디 찾을 때
	public MemberFormDto findNmPhone(String userNm, String pNum) {
		MemberFormDto memberFormDto =  memberRepository.findId(userNm, pNum);
		return memberFormDto;
	}
	
	//비밀번호 찾을 때 (힌트 찾기 페이지로 넘어감)
	public MemberFormDto haveMemberInfo(String userId, String pNum) {
		MemberFormDto memberFormDto =  memberRepository.findPassword(userId, pNum);
		return memberFormDto;
	}
	
	//힌트 찾을 때 (비밀번호 수정 페이지로 넘어감)
	public MemberFormDto findMemberHint(String hintA, String hintQ, String userId) {
		MemberFormDto memberFormDto =  memberRepository.findHint(hintA, hintQ, userId);
		return memberFormDto;
	}
	
	//비밀번호를 바꿔보자
	public void newPassword(MemberFormDto memberFormDto, PasswordEncoder passwordEncoder) {
		
		//현재 dto에는 로그인 아이디 데이터가 흐르기 떄문에 findByUserId를 쓴다
		Member member = memberRepository.findByUserId(memberFormDto.getUserId());
		
		//dto에 적용시킬 건 password 뿐이기 때문에 memberFormDto.getPassword()로 쓴다
		member.updatePassword(memberFormDto.getPassword(), passwordEncoder);
	}
	public void newPasswordpNum(MemberFormDto memberFormDto, PasswordEncoder passwordEncoder) {
		
		//현재 dto에는 로그인 아이디 데이터가 흐르기 떄문에 findByUserId를 쓴다
		Member member = memberRepository.findByUserId(memberFormDto.getUserId());
		
		//dto에 적용시킬 건 password 뿐이기 때문에 memberFormDto.getPassword()로 쓴다
		member.updatePassword(memberFormDto.getPassword(), passwordEncoder);
		member.updatepNum(memberFormDto.getPNum());
	}


}
