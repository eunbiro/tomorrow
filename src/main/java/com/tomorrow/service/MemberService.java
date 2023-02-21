package com.tomorrow.service;

import org.apache.groovy.parser.antlr4.util.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

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
		
		if(member == null) {
			throw new UsernameNotFoundException(userName);
		}
		
		//userDetails의 객체를 반환
		return User.builder()
				.username(member.getUserId())
				.password(member.getPassword())
				.roles(member.getRole().toString())
				.build();
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
		String imgName = "";	//기본 이미지 만들기
		String imgUrl = "";	//기본 이미지 url 만들기
		
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
	
}
